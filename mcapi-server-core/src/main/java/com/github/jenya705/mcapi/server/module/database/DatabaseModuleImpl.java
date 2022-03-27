package com.github.jenya705.mcapi.server.module.database;

import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.OnDisable;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.log.TimerTask;
import com.github.jenya705.mcapi.server.module.config.ConfigModule;
import com.github.jenya705.mcapi.server.module.database.cache.CacheConfig;
import com.github.jenya705.mcapi.server.module.database.cache.CacheStorage;
import com.github.jenya705.mcapi.server.module.database.cache.CacheStorageImpl;
import com.github.jenya705.mcapi.server.module.database.cache.FakeCacheStorage;
import com.github.jenya705.mcapi.server.module.database.safe.CacheDatabaseGetter;
import com.github.jenya705.mcapi.server.module.database.safe.DatabaseGetter;
import com.github.jenya705.mcapi.server.module.database.safe.StorageDatabaseGetter;
import com.github.jenya705.mcapi.server.module.database.storage.DatabaseStorage;
import com.github.jenya705.mcapi.server.module.storage.StorageModule;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.SneakyThrows;
import org.slf4j.Logger;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jenya705
 */
@Singleton
public class DatabaseModuleImpl extends AbstractApplicationModule implements SQLDatabaseModule {

    private final ServerApplication application;

    private final Map<String, DatabaseTypeInitializer> databaseTypeInitializers;

    private final DefaultDatabaseTypeInitializer defaultDatabaseTypeInitializer;

    private final DatabaseModuleConfig config;
    private final CacheConfig cacheConfig;

    private final Logger log;

    private CacheStorage cache;
    private DatabaseGetter safeSync;
    private DatabaseGetter safeSyncWithFuture;
    private DatabaseGetter safeAsync;
    private DatabaseStorage storage;
    private Connection connection;

    @Inject
    public DatabaseModuleImpl(ServerApplication application, ConfigModule configModule, StorageModule storageModule, Logger log) {
        super(application);
        this.log = log;
        this.application = application;
        databaseTypeInitializers = new HashMap<>();
        addTypeInitializer("mysql", new MySqlDatabaseInitializer(app(), this, storageModule));
        addTypeInitializer("sqlite", new SqliteDatabaseInitializer(app()));
        defaultDatabaseTypeInitializer = new DefaultDatabaseTypeInitializer(app(), this, storageModule);
        ConfigData configData = configModule
                .getConfig()
                .required("database");
        config = new DatabaseModuleConfig(configData);
        cacheConfig = new CacheConfig(configData.required("cache"));
    }

    protected Connection createConnection() {
        Connection connection = null;
        String loweredType = config.getType().toLowerCase(Locale.ROOT);
        if (databaseTypeInitializers.containsKey(loweredType)) {
            connection = databaseTypeInitializers
                    .get(loweredType)
                    .connection(
                            config.getHost(),
                            config.getUser(),
                            config.getPassword(),
                            config.getDatabase()
                    );
        }
        if (connection == null) {
            connection = defaultDatabaseTypeInitializer
                    .connection(config);
        }
        return connection;
    }

    protected void validateConnection() {
        if (connection == null) {
            TimerTask task = TimerTask.start(log, "Creating connection with database...");
            connection = createConnection();
            storage = createStorage(config.getType());
            storage.setup();
            safeAsync = new StorageDatabaseGetter(storage);
            if (config.isCacheEnabled()) {
                cache = new CacheStorageImpl(
                        application,
                        cacheConfig,
                        this
                );
                safeSync = new CacheDatabaseGetter(cache);
                safeSyncWithFuture = new CacheDatabaseGetter(cache.withFuture());
            }
            else {
                cache = new FakeCacheStorage(this);
                safeSync = safeAsync;
                safeSyncWithFuture = safeAsync;
            }
            task.complete();
        }
    }

    @OnDisable
    public void disable() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Override
    @SneakyThrows
    public void update(String sql, Object... objects) {
        debugSql(sql, objects);
        synchronized (this) {
            if (objects.length == 0) {
                Statement statement = getConnection().createStatement();
                statement.executeUpdate(sql);
            }
            else {
                PreparedStatement statement = getConnection().prepareStatement(sql);
                for (int i = 0; i < objects.length; ++i) {
                    statement.setObject(i + 1, objects[i]);
                }
                statement.executeUpdate();
            }
        }
    }

    @Override
    @SneakyThrows
    public ResultSet query(String sql, Object... objects) {
        debugSql(sql, objects);
        synchronized (this) {
            if (objects.length == 0) {
                return getConnection().createStatement().executeQuery(sql);
            }
            else {
                PreparedStatement statement = getConnection().prepareStatement(sql);
                for (int i = 0; i < objects.length; ++i) {
                    statement.setObject(i + 1, objects[i]);
                }
                return statement.executeQuery();
            }
        }
    }

    @Override
    public DatabaseStorage storage() {
        return storage;
    }

    @Override
    public CacheStorage cache() {
        return cache;
    }

    @Override
    public Connection getConnection() {
        validateConnection();
        return connection;
    }

    private DatabaseStorage createStorage(String sqlType) {
        String loweredSqlType = sqlType.toLowerCase(Locale.ROOT);
        DatabaseStorage created = null;
        if (databaseTypeInitializers.containsKey(loweredSqlType)) {
            created = databaseTypeInitializers
                    .get(loweredSqlType)
                    .storage();
        }
        if (created == null) {
            created = defaultDatabaseTypeInitializer.storage(config);
        }
        return created;
    }

    private void debugSql(String sql, Object... objects) {
        if (debug()) {
            log.info("SQL: {}#{}", sql, Arrays.asList(objects));
        }
    }

    @Override
    public DatabaseGetter safeSync() {
        return safeSync;
    }

    @Override
    public DatabaseGetter safeSyncWithFuture() {
        return safeSyncWithFuture;
    }

    @Override
    public DatabaseGetter safeAsync() {
        return safeAsync;
    }

    @Override
    public void addTypeInitializer(String type, DatabaseTypeInitializer typeInitializer) {
        databaseTypeInitializers.put(type.toLowerCase(Locale.ROOT), typeInitializer);
    }
}

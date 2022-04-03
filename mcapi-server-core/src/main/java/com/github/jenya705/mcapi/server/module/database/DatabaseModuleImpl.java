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
import com.github.jenya705.mcapi.server.module.database.mysql.MySqlDatabaseInitializer;
import com.github.jenya705.mcapi.server.module.database.safe.CacheDatabaseGetter;
import com.github.jenya705.mcapi.server.module.database.safe.DatabaseGetter;
import com.github.jenya705.mcapi.server.module.database.safe.StorageDatabaseGetter;
import com.github.jenya705.mcapi.server.module.database.sqlite.SqliteDatabaseInitializer;
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
import java.util.concurrent.atomic.AtomicReference;

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

    private final AtomicReference<CacheStorage> cache = new AtomicReference<>();
    private final AtomicReference<DatabaseGetter> safeSync = new AtomicReference<>();
    private final AtomicReference<DatabaseGetter> safeSyncWithFuture = new AtomicReference<>();
    private final AtomicReference<DatabaseGetter> safeAsync = new AtomicReference<>();
    private final AtomicReference<DatabaseStorage> storage = new AtomicReference<>();
    private final AtomicReference<Connection> connection = new AtomicReference<>();

    @Inject
    public DatabaseModuleImpl(ServerApplication application, ConfigModule configModule, StorageModule storageModule, Logger log) {
        super(application);
        this.log = log;
        this.application = application;
        databaseTypeInitializers = new HashMap<>();
        addTypeInitializer("mysql", new MySqlDatabaseInitializer(app(), this, storageModule));
        addTypeInitializer("sqlite", new SqliteDatabaseInitializer(app(), this, storageModule));
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

    @SneakyThrows
    protected synchronized void validateConnection() {
        // TODO make it thread safe
        if (connection.get() == null || connection.get().isClosed()) {
            TimerTask task = TimerTask.start(log, "Creating connection with database...");
            connection.set(createConnection());
            storage.set(createStorage(config.getType()));
            storage.get().setup();
            safeAsync.set(new StorageDatabaseGetter(storage.get()));
            if (config.isCacheEnabled()) {
                cache.set(
                        new CacheStorageImpl(
                                application,
                                cacheConfig,
                                this
                        )
                );
                safeSync.set(new CacheDatabaseGetter(cache.get()));
                safeSyncWithFuture.set(new CacheDatabaseGetter(cache.get().withFuture()));
            }
            else {
                cache.set(new FakeCacheStorage(this));
                safeSync.set(safeAsync.get());
                safeSyncWithFuture.set(safeAsync.get());
            }
            task.complete();
        }
    }

    @OnDisable
    public void disable() throws SQLException {
        if (connection.get() != null && !connection.get().isClosed()) {
            connection.get().close();
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
        validateConnection();
        return storage.get();
    }

    @Override
    public CacheStorage cache() {
        validateConnection();
        return cache.get();
    }

    @Override
    public Connection getConnection() {
        validateConnection();
        return connection.get();
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
        validateConnection();
        return safeSync.get();
    }

    @Override
    public DatabaseGetter safeSyncWithFuture() {
        validateConnection();
        return safeSyncWithFuture.get();
    }

    @Override
    public DatabaseGetter safeAsync() {
        validateConnection();
        return safeAsync.get();
    }

    @Override
    public void addTypeInitializer(String type, DatabaseTypeInitializer typeInitializer) {
        databaseTypeInitializers.put(type.toLowerCase(Locale.ROOT), typeInitializer);
    }
}

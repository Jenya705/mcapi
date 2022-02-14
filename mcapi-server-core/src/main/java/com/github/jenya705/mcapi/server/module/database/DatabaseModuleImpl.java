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
import com.github.jenya705.mcapi.server.module.storage.StorageModule;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jenya705
 */
@Slf4j
@Singleton
public class DatabaseModuleImpl extends AbstractApplicationModule implements DatabaseModule {

    private final Map<String, DatabaseTypeInitializer> databaseTypeInitializers;

    private final DefaultDatabaseTypeInitializer defaultDatabaseTypeInitializer;

    private final Connection connection;
    private final DatabaseStorage storage;

    private final DatabaseModuleConfig config;

    private final CacheStorage cache;
    private final DatabaseGetter safeSync;
    private final DatabaseGetter safeSyncWithFuture;
    private final DatabaseGetter safeAsync;

    @Inject
    public DatabaseModuleImpl(ServerApplication application, ConfigModule configModule, StorageModule storageModule) {
        super(application);
        databaseTypeInitializers = new HashMap<>();
        addTypeInitializer("mysql", new MySqlDatabaseInitializer(app(), this, storageModule));
        addTypeInitializer("sqlite", new SqliteDatabaseInitializer(app()));
        defaultDatabaseTypeInitializer = new DefaultDatabaseTypeInitializer(app(), this, storageModule);
        ConfigData configData = configModule
                .getConfig()
                .required("database");
        config = new DatabaseModuleConfig(configData);
        TimerTask task = TimerTask.start(log, "Creating connection with %s...", config.getType());
        connection = createConnection();
        task.complete();
        task.start("Loading storage...");
        storage = createStorage(config.getType());
        task.complete();
        storage.setup();
        safeAsync = new StorageDatabaseGetter(storage);
        if (config.isDisableCaching()) {
            cache = new FakeCacheStorage(this);
            safeSync = safeAsync;
            safeSyncWithFuture = safeAsync;
        }
        else {
            cache = new CacheStorageImpl(
                    application,
                    new CacheConfig(
                            configData
                                    .required("cache")
                    ),
                    this
            );
            safeSync = new CacheDatabaseGetter(cache);
            safeSyncWithFuture = new CacheDatabaseGetter(cache.withFuture());
        }
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

    @OnDisable
    public void disable() throws SQLException {
        if (connection != null) connection.close();
    }

    @Override
    @SneakyThrows
    public void update(String sql, Object... objects) {
        synchronized (this) {
            if (objects.length == 0) {
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);
            }
            else {
                PreparedStatement statement = connection.prepareStatement(sql);
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
        synchronized (this) {
            if (objects.length == 0) {
                return connection.createStatement().executeQuery(sql);
            }
            else {
                PreparedStatement statement = connection.prepareStatement(sql);
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

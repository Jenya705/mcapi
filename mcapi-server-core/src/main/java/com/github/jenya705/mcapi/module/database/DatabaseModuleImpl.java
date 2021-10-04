package com.github.jenya705.mcapi.module.database;

import com.github.jenya705.mcapi.AbstractApplicationModule;
import com.github.jenya705.mcapi.OnDisable;
import com.github.jenya705.mcapi.OnInitializing;
import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.log.TimerTask;
import com.github.jenya705.mcapi.module.config.ConfigModule;
import com.github.jenya705.mcapi.module.database.cache.CacheConfig;
import com.github.jenya705.mcapi.module.database.cache.CacheStorage;
import com.github.jenya705.mcapi.module.database.cache.CacheStorageImpl;
import com.github.jenya705.mcapi.module.database.safe.CacheDatabaseGetter;
import com.github.jenya705.mcapi.module.database.safe.DatabaseGetter;
import com.github.jenya705.mcapi.module.database.safe.StorageDatabaseGetter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Jenya705
 */
@Slf4j
public class DatabaseModuleImpl extends AbstractApplicationModule implements DatabaseModule {

    private final Map<String, DatabaseTypeInitializer> databaseTypeInitializers = new HashMap<>();

    private DefaultDatabaseTypeInitializer defaultDatabaseTypeInitializer;

    private Connection connection;
    private DatabaseStorage storage;

    private DatabaseModuleConfig config;

    private CacheStorage cache;
    private DatabaseGetter safeSync;
    private DatabaseGetter safeSyncWithFuture;
    private DatabaseGetter safeAsync;

    private final Object block = new Object();

    @OnInitializing
    public void initialize() {
        addTypeInitializer("mysql", new MySqlDatabaseInitializer(app()));
        addTypeInitializer("sqlite", new SqliteDatabaseInitializer(app()));
        defaultDatabaseTypeInitializer = new DefaultDatabaseTypeInitializer(app());
        ConfigData configData =
                bean(ConfigModule.class)
                        .getConfig()
                        .required("database");
        config = new DatabaseModuleConfig(configData);
        cache = new CacheStorageImpl(
                app(),
                new CacheConfig(
                        configData
                                .required("cache")
                )
        );
        TimerTask task = TimerTask.start(log, String.format("Creating connection with %s...", config.getType()));
        createConnection();
        task.complete();
        task.start("Loading storage...");
        storage = createStorage(config.getType());
        task.complete();
        storage.setup();
        safeAsync = new StorageDatabaseGetter(storage);
        safeSync = new CacheDatabaseGetter(cache);
        safeSyncWithFuture = new CacheDatabaseGetter(cache.withFuture());
    }

    protected void createConnection() {
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
    }

    @OnDisable
    public void disable() throws SQLException {
        if (connection != null) connection.close();
    }

    @Override
    @SneakyThrows
    public void update(String sql, Object... objects) {
        synchronized (block) {
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
        synchronized (block) {
            if (objects.length == 0) {
                Statement statement = connection.createStatement();
                return statement.executeQuery(sql);
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

    public DatabaseStorage createStorage(String sqlType) {
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

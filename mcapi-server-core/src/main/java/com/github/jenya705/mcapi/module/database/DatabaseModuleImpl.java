package com.github.jenya705.mcapi.module.database;

import com.github.jenya705.mcapi.AbstractApplicationModule;
import com.github.jenya705.mcapi.OnDisable;
import com.github.jenya705.mcapi.OnInitializing;
import com.github.jenya705.mcapi.data.ConfigData;
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

/**
 * @author Jenya705
 */
@Slf4j
public class DatabaseModuleImpl extends AbstractApplicationModule implements DatabaseModule {

    private static final String urlFormat = "jdbc:%s://%s/%s";
    private static final String sqliteUrlFormat = "jdbc:sqlite://%s";

    private Connection connection;
    private DatabaseModuleConfig config;
    private DatabaseStorage storage;
    private CacheStorage cache;

    private DatabaseGetter safeSync;
    private DatabaseGetter safeSyncWithFuture;
    private DatabaseGetter safeAsync;

    private final Object block = new Object();

    @OnInitializing
    public void initialize() throws ClassNotFoundException, SQLException, IOException {
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
        log.info(String.format("Creating connection with %s...", config.getType()));
        loadDriver(config.getType());
        createConnection();
        log.info(String.format("Done! (Creating connection with %s...)", config.getType()));
        log.info("Loading scripts...");
        storage = createStorage(config.getType());
        log.info("Done! (Loading scripts...)");
        storage.setup();
        safeAsync = new StorageDatabaseGetter(storage);
        safeSync = new CacheDatabaseGetter(cache);
        safeSyncWithFuture = new CacheDatabaseGetter(cache.withFuture());
    }

    protected void loadDriver(String type) throws ClassNotFoundException {
        if (type.equalsIgnoreCase("mysql")) {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        else if (type.equalsIgnoreCase("sqlite")) {
            Class.forName("org.sqlite.jdbc4.JDBC4Connection");
        }
        else {
            throw new IllegalArgumentException("Type is not exist in default context");
        }
    }

    protected void createConnection() throws SQLException {
        if (config.getType().equalsIgnoreCase("sqlite")) {
            connection = DriverManager.getConnection(
                    String.format(
                            sqliteUrlFormat,
                            core()
                                    .getPluginFile("database.db")
                                    .getAbsolutePath()
                    )
            );
        }
        else {
            connection = DriverManager.getConnection(
                    String.format(
                            urlFormat,
                            config.getType(),
                            config.getHost(),
                            config.getDatabase()
                    ),
                    config.getUser(),
                    config.getPassword()
            );
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

    public DatabaseStorage createStorage(String sqlType) throws IOException {
        if (sqlType.equalsIgnoreCase("mysql")) {
            return new MySqlDatabaseStorage(app(), this);
        }
        else {
            return new DatabaseStorageImpl(app(), this, sqlType);
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
}

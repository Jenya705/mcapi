package com.github.jenya705.mcapi.module.database;

import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.OnDisable;
import com.github.jenya705.mcapi.OnInitializing;
import com.github.jenya705.mcapi.module.config.ConfigModule;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.*;

/**
 * @author Jenya705
 */
@Slf4j
public class DatabaseModuleImpl implements DatabaseModule, BaseCommon {

    private static final String urlFormat = "jdbc:%s://%s/%s";
    private static final String sqliteUrlFormat = "jdbc:sqlite://%s";

    private Connection connection;
    private DatabaseModuleConfig config;
    private DatabaseScriptStorage storage;

    private final Object block = new Object();

    @OnInitializing
    public void initialize() throws ClassNotFoundException, SQLException, IOException {
        config = new DatabaseModuleConfig(
                bean(ConfigModule.class)
                        .getConfig()
                        .required("database")
        );
        log.info(String.format("Creating connection with %s...", config.getType()));
        loadDriver(config.getType());
        createConnection();
        log.info(String.format("Done! (Creating connection with %s...)", config.getType()));
        log.info("Loading scripts...");
        storage = DatabaseModuleImpl.of(this, config.getType());
        log.info("Done! (Loading scripts...)");
        storage.setup();
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
    public DatabaseScriptStorage storage() {
        return storage;
    }

    public static DatabaseScriptStorage of(DatabaseModule databaseModule, String sqlType) throws IOException {
        if (sqlType.equalsIgnoreCase("mysql")) {
            return new MySqlDatabaseScriptStorage(databaseModule);
        }
        else {
            return new DatabaseScriptStorageImpl(databaseModule, sqlType);
        }
    }

}

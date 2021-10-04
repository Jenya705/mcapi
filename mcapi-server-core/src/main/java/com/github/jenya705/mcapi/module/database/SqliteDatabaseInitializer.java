package com.github.jenya705.mcapi.module.database;

import com.github.jenya705.mcapi.ServerApplication;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class SqliteDatabaseInitializer implements DatabaseTypeInitializer {

    private static final String sqliteUrlFormat = "jdbc:sqlite://%s";

    private final ServerApplication application;

    @Override
    @SneakyThrows
    public Connection connection(String host, String user, String password, String database) {
        Class.forName("org.sqlite.jdbc4.JDBC4Connection");
        return DriverManager.getConnection(
                String.format(
                        sqliteUrlFormat,
                        application
                                .getCore()
                                .getPluginFile("database.db")
                                .getAbsolutePath()
                )
        );
    }
}

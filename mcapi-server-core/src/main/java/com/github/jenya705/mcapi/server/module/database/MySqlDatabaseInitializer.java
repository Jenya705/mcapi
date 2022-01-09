package com.github.jenya705.mcapi.server.module.database;

import com.github.jenya705.mcapi.server.ServerApplication;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.sql.Connection;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class MySqlDatabaseInitializer implements DatabaseTypeInitializer {

    private final ServerApplication application;

    @Override
    @SneakyThrows
    public Connection connection(String host, String user, String password, String database) {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return null;
    }

    @Override
    @SneakyThrows
    public DatabaseStorage storage() {
        return new MySqlDatabaseStorage(application, application.getBean(DatabaseModule.class));
    }
}

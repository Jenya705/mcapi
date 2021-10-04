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
public class DefaultDatabaseTypeInitializer {

    private static final String urlFormat = "jdbc:%s://%s/%s";

    private final ServerApplication application;

    @SneakyThrows
    public Connection connection(DatabaseModuleConfig config) {
        return DriverManager.getConnection(
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

    @SneakyThrows
    public DatabaseStorage storage(DatabaseModuleConfig config) {
        return new DatabaseStorageImpl(
                application,
                application.getBean(DatabaseModule.class),
                config.getType()
        );
    }

}

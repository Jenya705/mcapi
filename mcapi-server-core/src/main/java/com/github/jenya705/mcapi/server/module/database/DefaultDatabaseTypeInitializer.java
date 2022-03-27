package com.github.jenya705.mcapi.server.module.database;

import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.module.database.storage.DatabaseStorage;
import com.github.jenya705.mcapi.server.module.database.storage.DatabaseStorageImpl;
import com.github.jenya705.mcapi.server.module.storage.StorageModule;
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
    private final SQLDatabaseModule databaseModule;
    private final StorageModule storageModule;

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
                databaseModule,
                storageModule,
                config.getType()
        );
    }

}

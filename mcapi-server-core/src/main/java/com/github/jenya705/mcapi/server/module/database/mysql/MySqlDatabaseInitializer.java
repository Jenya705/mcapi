package com.github.jenya705.mcapi.server.module.database.mysql;

import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.module.database.DatabaseTypeInitializer;
import com.github.jenya705.mcapi.server.module.database.SQLDatabaseModule;
import com.github.jenya705.mcapi.server.module.database.storage.DatabaseStorage;
import com.github.jenya705.mcapi.server.module.storage.StorageModule;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.sql.Connection;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class MySqlDatabaseInitializer implements DatabaseTypeInitializer {

    private final ServerApplication application;
    private final SQLDatabaseModule databaseModule;
    private final StorageModule storageModule;

    @Override
    @SneakyThrows
    public Connection connection(String host, String user, String password, String database) {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return null;
    }

    @Override
    @SneakyThrows
    public DatabaseStorage storage() {
        return new MySqlDatabaseStorage(application, databaseModule, storageModule);
    }
}
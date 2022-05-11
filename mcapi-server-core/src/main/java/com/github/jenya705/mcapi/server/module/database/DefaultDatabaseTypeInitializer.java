package com.github.jenya705.mcapi.server.module.database;

import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.module.database.sql.SQLConnectionManager;
import com.github.jenya705.mcapi.server.module.database.sql.SQLDatabaseModule;
import com.github.jenya705.mcapi.server.module.database.sql.SupplierSQLConnectionManager;
import com.github.jenya705.mcapi.server.module.database.storage.DatabaseStorage;
import com.github.jenya705.mcapi.server.module.database.storage.DatabaseStorageImpl;
import com.github.jenya705.mcapi.server.module.storage.StorageModule;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class DefaultDatabaseTypeInitializer {

    @RequiredArgsConstructor
    private static class DataSourceConnectionSupplier implements SupplierSQLConnectionManager.ConnectionSupplier {

        private final HikariDataSource dataSource;

        @Override
        public Connection get() throws SQLException {
            return dataSource.getConnection();
        }
    }

    private static final String urlFormat = "jdbc:%s://%s/%s?autoReconnect=true";

    private final ServerApplication application;
    private final SQLDatabaseModule databaseModule;
    private final StorageModule storageModule;

    @SneakyThrows
    public SQLConnectionManager connection(DatabaseModuleConfig config) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(String.format(
                urlFormat,
                config.getType(),
                config.getHost(),
                config.getDatabase()
        ));
        hikariConfig.setUsername(config.getUser());
        hikariConfig.setPassword(config.getPassword());
        return new SupplierSQLConnectionManager(
                new DataSourceConnectionSupplier(new HikariDataSource(hikariConfig))
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

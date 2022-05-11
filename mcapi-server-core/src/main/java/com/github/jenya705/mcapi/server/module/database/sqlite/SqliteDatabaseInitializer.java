package com.github.jenya705.mcapi.server.module.database.sqlite;

import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.module.database.DatabaseTypeInitializer;
import com.github.jenya705.mcapi.server.module.database.sql.SQLConnectionManager;
import com.github.jenya705.mcapi.server.module.database.sql.SQLDatabaseModule;
import com.github.jenya705.mcapi.server.module.database.sql.SingleSQLConnectionManager;
import com.github.jenya705.mcapi.server.module.database.storage.DatabaseStorage;
import com.github.jenya705.mcapi.server.module.storage.StorageModule;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.sql.DriverManager;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class SqliteDatabaseInitializer implements DatabaseTypeInitializer {

    private static final String sqliteUrlFormat = "jdbc:sqlite://%s";

    private final ServerApplication application;
    private final SQLDatabaseModule databaseModule;
    private final StorageModule storageModule;

    @Override
    @SneakyThrows
    public SQLConnectionManager connection(String host, String user, String password, String database) {
        Class.forName("org.sqlite.jdbc4.JDBC4Connection");
        return new SingleSQLConnectionManager(
                DriverManager.getConnection(
                        String.format(
                                sqliteUrlFormat,
                                application
                                        .getCore()
                                        .getAbsolutePath("database.db")
                        )
                )
        );
    }

    @Override
    @SneakyThrows
    public DatabaseStorage storage() {
        return new SqliteDatabaseStorage(application, databaseModule, storageModule);
    }
}

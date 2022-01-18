package com.github.jenya705.mcapi.mock.database;

import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.module.config.ConfigModule;
import com.github.jenya705.mcapi.server.module.database.DatabaseModuleImpl;
import com.github.jenya705.mcapi.server.module.database.DatabaseTypeInitializer;
import com.google.inject.Inject;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class MockDatabaseModule extends DatabaseModuleImpl {

    @Inject
    public MockDatabaseModule(ServerApplication application, ConfigModule configModule) {
        super(application, configModule);
        addTypeInitializer("h2", new DatabaseTypeInitializer() {
            @Override
            @SneakyThrows
            public Connection connection(String host, String user, String password, String database) {
                Class.forName("org.h2.Driver");
                Connection connection = DriverManager.getConnection(
                        String.format("jdbc:h2:mem:%s", database), user, password
                );
                connection.createStatement().executeUpdate("DROP ALL OBJECTS");
                return connection;
            }
        });
    }

}

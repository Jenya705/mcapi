package com.github.jenya705.mcapi.mock.database;

import com.github.jenya705.mcapi.server.module.database.DatabaseModuleImpl;
import com.github.jenya705.mcapi.server.module.database.DatabaseTypeInitializer;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class MockDatabaseModule extends DatabaseModuleImpl {

    public MockDatabaseModule() {
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

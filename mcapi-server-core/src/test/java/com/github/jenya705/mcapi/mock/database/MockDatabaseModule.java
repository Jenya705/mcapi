package com.github.jenya705.mcapi.mock.database;

import com.github.jenya705.mcapi.module.database.DatabaseModule;
import com.github.jenya705.mcapi.module.database.DatabaseModuleImpl;
import com.github.jenya705.mcapi.module.database.DatabaseTypeInitializer;
import lombok.SneakyThrows;
import lombok.experimental.Delegate;

import java.sql.Connection;
import java.sql.DriverManager;

public class MockDatabaseModule implements DatabaseModule {

    @Delegate
    private final DatabaseModule delegate = new DatabaseModuleImpl();

    public MockDatabaseModule() {
        delegate.addTypeInitializer("h2", new DatabaseTypeInitializer() {
            @Override
            @SneakyThrows
            public Connection connection(String host, String user, String password, String database) {
                Class.forName("org.h2.Driver");
                return DriverManager.getConnection(
                        String.format("jdbc:h2:mem:%s", database), user, password
                );
            }
        });
    }

}

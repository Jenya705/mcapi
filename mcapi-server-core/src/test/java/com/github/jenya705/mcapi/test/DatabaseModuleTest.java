package com.github.jenya705.mcapi.test;

import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.module.database.DatabaseModule;
import com.github.jenya705.mcapi.module.database.DatabaseModuleImpl;
import com.github.jenya705.mcapi.module.database.DatabaseTypeInitializer;
import com.github.jenya705.mcapi.test.mock.ConfigModuleMock;
import com.github.jenya705.mcapi.test.mock.ServerApplicationMock;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Jenya705
 */
public class DatabaseModuleTest {

    @SneakyThrows
    private DatabaseModule initialize() {
        ServerApplication application = new ServerApplicationMock();
        DatabaseModule module = new DatabaseModuleImpl();
        module.addTypeInitializer("h2", new DatabaseTypeInitializer() {
            @Override
            @SneakyThrows
            public Connection connection(String host, String user, String password, String database) {
                Class.forName("org.h2.Driver");
                return DriverManager.getConnection(
                        String.format("h2:jdbc:mem:%s", database), user, password
                );
            }
        });
        ConfigModuleMock configModuleMock = new ConfigModuleMock();
        application.start();
        application.stop();
        return module;
    }

}

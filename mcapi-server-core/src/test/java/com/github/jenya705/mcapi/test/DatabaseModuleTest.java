package com.github.jenya705.mcapi.test;

import com.github.jenya705.mcapi.Bean;
import com.github.jenya705.mcapi.OnInitializing;
import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.entity.BotEntity;
import com.github.jenya705.mcapi.module.database.DatabaseModule;
import com.github.jenya705.mcapi.module.database.DatabaseModuleImpl;
import com.github.jenya705.mcapi.module.database.DatabaseTypeInitializer;
import com.github.jenya705.mcapi.test.mock.ConfigModuleMock;
import com.github.jenya705.mcapi.test.mock.ServerApplicationMock;
import com.github.jenya705.mcapi.util.ConfigDataBuilder;
import com.github.jenya705.mcapi.util.TokenUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.UUID;

/**
 * @author Jenya705
 */
public class DatabaseModuleTest {

    @SneakyThrows
    private ServerApplication initialize() {
        ServerApplication application = new ServerApplicationMock();
        DatabaseModule module = new DatabaseModuleImpl();
        module.addTypeInitializer("h2", new DatabaseTypeInitializer() {
            @Override
            @SneakyThrows
            public Connection connection(String host, String user, String password, String database) {
                Class.forName("org.h2.Driver");
                return DriverManager.getConnection(
                        String.format("jdbc:h2:mem:%s", database), user, password
                );
            }
        });
        application.addBean(module);
        ConfigModuleMock configModuleMock = new ConfigModuleMock();
        application.addBean(configModuleMock);
        application.addBean(new Object(){
            @Bean
            private ConfigModuleMock configModuleMock;

            @OnInitializing(priority = 1)
            public void initialize() {
                configModuleMock.joinConfig(
                        ConfigDataBuilder
                                .empty()
                                .directory("database", it -> it
                                        .put("type", "h2")
                                )
                                .getData()
                );
            }
        });
        application.start();
        return application;
    }

    @Test
    public void containerTest() {
        ServerApplication application = initialize();
        DatabaseModule databaseModule = application.getBean(DatabaseModule.class);
        String generatedToken = TokenUtils.generateToken();
        UUID ownerUUID = UUID.randomUUID();
        databaseModule.storage().save(new BotEntity(generatedToken, "test", ownerUUID, 1));
        Assertions.assertEquals(
                databaseModule.storage().findBotById(1),
                new BotEntity(generatedToken, "test", ownerUUID, 1)
        );
        Assertions.assertEquals(
                databaseModule.storage().findBotByToken(generatedToken),
                new BotEntity(generatedToken, "test", ownerUUID, 1)
        );
    }

    @Test
    public void cachingTest() {
        ServerApplication application = initialize();
        DatabaseModule databaseModule = application.getBean(DatabaseModule.class);
        String generatedToken = TokenUtils.generateToken();
        UUID ownerUUID = UUID.randomUUID();
        databaseModule.storage().save(new BotEntity(generatedToken, "test", ownerUUID, 1));
        Assertions.assertEquals(
                databaseModule.cache().getCachedBot(1),
                new BotEntity(generatedToken, "test", ownerUUID, 1)
        );
        Assertions.assertEquals(
                databaseModule.cache().getCachedBot(generatedToken),
                new BotEntity(generatedToken, "test", ownerUUID, 1)
        );
    }

    @Test
    public void cachingConcurrentTest() {
        ServerApplication application = initialize();
        DatabaseModule databaseModule = application.getBean(DatabaseModule.class);
        BotEntity[] botEntities = new BotEntity[30];
        for (int i = 0; i < botEntities.length; ++i) {
            botEntities[i] = new BotEntity(
                    TokenUtils.generateToken(),
                    "test" + i,
                    UUID.randomUUID(), i
            );
            databaseModule.storage().save(botEntities[i]);
        }
        for (int i = 0; i < 20; ++i) {
            Assertions.assertNull(
                    databaseModule.cache().getCachedBot(i)
            );
        }
        for (int i = 20; i < 30; ++i) {
            Assertions.assertEquals(
                    databaseModule.cache().getCachedBot(i),
                    botEntities[i]
            );
        }
    }


}

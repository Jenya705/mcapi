package com.github.jenya705.mcapi.test;

import com.github.jenya705.mcapi.entity.BotEntity;
import com.github.jenya705.mcapi.mock.MockServerApplication;
import com.github.jenya705.mcapi.module.database.DatabaseModule;
import com.github.jenya705.mcapi.util.TokenUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * @author Jenya705
 */
public class DatabaseModuleTest {

    @Test
    public void saveTest() {
        MockServerApplication application = MockServerApplication.mock().run();
        DatabaseModule databaseModule = application.getBean(DatabaseModule.class);
        databaseModule
                .storage()
                .save(TestUtils.sampleBotEntity);
        Assertions.assertEquals(
                TestUtils.sampleBotEntity,
                databaseModule
                        .storage()
                        .findBotById(TestUtils.sampleBotEntity.getId())
        );
        Assertions.assertEquals(
                TestUtils.sampleBotEntity,
                databaseModule
                        .storage()
                        .findBotByToken(TestUtils.sampleBotEntity.getToken())
        );
    }

    @Test
    public void cacheTest() {
        MockServerApplication application = MockServerApplication.mock().run();
        DatabaseModule databaseModule = application.getBean(DatabaseModule.class);
        databaseModule
                .storage()
                .save(TestUtils.sampleBotEntity);
        Assertions.assertEquals(
                TestUtils.sampleBotEntity,
                databaseModule
                        .cache()
                        .getCachedBot(TestUtils.sampleBotEntity.getId())
        );
        Assertions.assertEquals(
                TestUtils.sampleBotEntity,
                databaseModule
                        .cache()
                        .getCachedBot(TestUtils.sampleBotEntity.getToken())
        );
    }

    // @Test - ignore
    public void cacheConcurrentTest() {
        MockServerApplication application = MockServerApplication.mock().run();
        DatabaseModule databaseModule = application.getBean(DatabaseModule.class);
        BotEntity[] botEntities = new BotEntity[1500];
        for (int i = 0; i < botEntities.length; ++i) {
            botEntities[i] = BotEntity
                    .builder()
                    .id(i + 1)
                    .name("bot" + i)
                    .owner(UUID.randomUUID())
                    .token(TokenUtils.generateToken())
                    .build();
            databaseModule
                    .storage()
                    .save(botEntities[i]);
        }
        for (int i = 0; i < botEntities.length - 500; ++i) {
            Assertions.assertNull(
                    databaseModule
                            .cache()
                            .getCachedBot(botEntities[i].getId())
            );
            Assertions.assertNull(
                    databaseModule
                            .cache()
                            .getCachedBot(botEntities[i].getToken())
            );
        }
        for (int i = botEntities.length - 500; i < botEntities.length; ++i) {
            Assertions.assertEquals(
                    botEntities[i],
                    databaseModule
                            .cache()
                            .getCachedBot(botEntities[i].getId())
            );
            Assertions.assertEquals(
                    botEntities[i],
                    databaseModule
                            .cache()
                            .getCachedBot(botEntities[i].getToken())
            );
        }
    }
}

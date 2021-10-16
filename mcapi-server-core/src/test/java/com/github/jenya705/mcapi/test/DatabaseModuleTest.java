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

    private final BotEntity sampleBotEntity = BotEntity
            .builder()
            .owner(UUID.randomUUID())
            .token(TokenUtils.generateToken())
            .name("some")
            .id(1)
            .build();

    @Test
    public void saveTest() {
        MockServerApplication application = MockServerApplication.mock().run();
        DatabaseModule databaseModule = application.getBean(DatabaseModule.class);
        databaseModule
                .storage()
                .save(sampleBotEntity);
        Assertions.assertEquals(
                sampleBotEntity,
                databaseModule
                        .storage()
                        .findBotById(sampleBotEntity.getId())
        );
        Assertions.assertEquals(
                sampleBotEntity,
                databaseModule
                        .storage()
                        .findBotByToken(sampleBotEntity.getToken())
        );
    }

    @Test
    public void cacheTest() {
        MockServerApplication application = MockServerApplication.mock().run();
        DatabaseModule databaseModule = application.getBean(DatabaseModule.class);
        databaseModule
                .storage()
                .save(sampleBotEntity);
        Assertions.assertEquals(
                sampleBotEntity,
                databaseModule
                        .cache()
                        .getCachedBot(sampleBotEntity.getId())
        );
        Assertions.assertEquals(
                sampleBotEntity,
                databaseModule
                        .cache()
                        .getCachedBot(sampleBotEntity.getToken())
        );
    }

    @Test
    public void cacheConcurrentTest() {
        MockServerApplication application = MockServerApplication.mock().run();
        DatabaseModule databaseModule = application.getBean(DatabaseModule.class);
        BotEntity[] botEntities = new BotEntity[30];
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
        for (int i = 0; i < botEntities.length - 10; ++i) {
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
        for (int i = botEntities.length - 10; i < botEntities.length; ++i) {
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

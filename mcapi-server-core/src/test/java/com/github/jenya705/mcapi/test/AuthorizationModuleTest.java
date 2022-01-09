package com.github.jenya705.mcapi.test;

import com.github.jenya705.mcapi.error.AuthorizationBadTokenException;
import com.github.jenya705.mcapi.error.AuthorizationFormatException;
import com.github.jenya705.mcapi.mock.MockServerApplication;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.module.authorization.AuthorizationModule;
import com.github.jenya705.mcapi.server.module.database.DatabaseModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jenya705
 */
public class AuthorizationModuleTest {

    @Test
    public void goodTest() {
        MockServerApplication application = MockServerApplication.mock().run();
        application
                .getBean(DatabaseModule.class)
                .storage()
                .save(TestUtils.sampleBotEntity);
        AuthorizationModule authorizationModule = application.getBean(AuthorizationModule.class);
        AbstractBot bot = authorizationModule.bot("Bot " + TestUtils.sampleBotEntity.getToken());
        Assertions.assertEquals(TestUtils.sampleBotEntity, bot.getEntity());
        bot = authorizationModule.rawBot(TestUtils.sampleBotEntity.getToken());
        Assertions.assertEquals(TestUtils.sampleBotEntity, bot.getEntity());
    }

    @Test
    public void formatExceptionTest() {
        MockServerApplication application = MockServerApplication.mock().run();
        application
                .getBean(DatabaseModule.class)
                .storage()
                .save(TestUtils.sampleBotEntity);
        AuthorizationModule authorizationModule = application.getBean(AuthorizationModule.class);
        Assertions.assertThrows(
                AuthorizationFormatException.class,
                () -> authorizationModule.bot("Bo " + TestUtils.sampleBotEntity.getToken())
        );
    }

    @Test
    public void badTokenTest() {
        MockServerApplication application = MockServerApplication.mock().run();
        AuthorizationModule authorizationModule = application.getBean(AuthorizationModule.class);
        Assertions.assertThrows(
                AuthorizationBadTokenException.class,
                () -> authorizationModule.bot("Bot " + TestUtils.sampleBotEntity.getToken())
        );
    }

}

package com.github.jenya705.mcapi.server.module.authorization;

import com.github.jenya705.mcapi.error.AuthorizationBadTokenException;
import com.github.jenya705.mcapi.error.AuthorizationFormatException;
import com.github.jenya705.mcapi.server.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.Bean;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.entity.BotObject;
import com.github.jenya705.mcapi.server.module.database.DatabaseModule;
import com.github.jenya705.mcapi.server.module.storage.StorageModule;

/**
 * @author Jenya705
 */
public class AuthorizationModuleImpl extends AbstractApplicationModule implements AuthorizationModule {

    @Bean
    private DatabaseModule databaseModule;

    @Bean
    private StorageModule storage;

    @Override
    public AbstractBot bot(String authorization) {
        if (authorization == null) {
            throw AuthorizationFormatException.create();
        }
        String[] splitAuthorization = authorization.split(" ");
        if (splitAuthorization.length != 2 ||
                !splitAuthorization[0].equalsIgnoreCase("bot")) {
            throw AuthorizationFormatException.create();
        }
        return rawBot(splitAuthorization[1]);
    }

    @Override
    public AbstractBot rawBot(String token) {
        BotEntity bot = databaseModule.storage().findBotByToken(token);
        if (bot == null) {
            throw AuthorizationBadTokenException.create();
        }
        return new BotObject(bot, databaseModule.storage(), storage);
    }
}

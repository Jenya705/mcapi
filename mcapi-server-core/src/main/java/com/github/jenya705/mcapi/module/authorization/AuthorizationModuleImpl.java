package com.github.jenya705.mcapi.module.authorization;

import com.github.jenya705.mcapi.AbstractApplicationModule;
import com.github.jenya705.mcapi.Bean;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.entity.BotEntity;
import com.github.jenya705.mcapi.entity.BotObject;
import com.github.jenya705.mcapi.error.AuthorizationBadTokenException;
import com.github.jenya705.mcapi.error.AuthorizationFormatException;
import com.github.jenya705.mcapi.module.database.DatabaseModule;
import com.github.jenya705.mcapi.module.storage.StorageModule;

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

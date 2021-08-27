package com.github.jenya705.mcapi.module.authorization;

import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.OnStartup;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.entity.BotEntity;
import com.github.jenya705.mcapi.entity.BotObject;
import com.github.jenya705.mcapi.error.AuthorizationBadTokenException;
import com.github.jenya705.mcapi.error.AuthorizationFormatException;
import com.github.jenya705.mcapi.module.database.DatabaseModule;
import com.github.jenya705.mcapi.module.database.DatabaseScriptStorage;
import com.github.jenya705.mcapi.module.storage.StorageModule;

/**
 * @author Jenya705
 */
public class AuthorizationModuleImpl implements AuthorizationModule, BaseCommon {

    private DatabaseScriptStorage scriptStorage;
    private StorageModule storage;

    @OnStartup
    public void start() {
        scriptStorage = bean(DatabaseModule.class).storage();
        storage = bean(StorageModule.class);
    }

    @Override
    public AbstractBot bot(String authorization) {
        if (authorization == null) {
            throw new AuthorizationFormatException();
        }
        String[] splitAuthorization = authorization.split(" ");
        if (splitAuthorization.length != 2 ||
                !splitAuthorization[0].equalsIgnoreCase("bot")) {
            throw new AuthorizationFormatException();
        }
        BotEntity bot = scriptStorage.findBotByToken(splitAuthorization[1]);
        if (bot == null) {
            throw new AuthorizationBadTokenException();
        }
        return new BotObject(bot, scriptStorage, storage);
    }
}
package com.github.jenya705.mcapi.server.module.authorization;

import com.github.jenya705.mcapi.error.AuthorizationBadTokenException;
import com.github.jenya705.mcapi.error.AuthorizationFormatException;
import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.entity.BotObject;
import com.github.jenya705.mcapi.server.module.authorization.debug.DebugBotFactory;
import com.github.jenya705.mcapi.server.module.database.storage.EventDatabaseStorage;
import com.github.jenya705.mcapi.server.module.storage.StorageModule;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Jenya705
 */
@Singleton
public class AuthorizationModuleImpl extends AbstractApplicationModule implements AuthorizationModule {

    private final EventDatabaseStorage databaseStorage;
    private final StorageModule storage;
    private final DebugBotFactory debugBotFactory;

    @Inject
    public AuthorizationModuleImpl(ServerApplication application, EventDatabaseStorage databaseStorage,
                                   StorageModule storage, DebugBotFactory debugBotFactory) {
        super(application);
        this.databaseStorage = databaseStorage;
        this.storage = storage;
        this.debugBotFactory = debugBotFactory;
    }

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
        AbstractBot debugBot = debugBotFactory.create(token);
        if (debugBot != null) return debugBot;
        BotEntity bot = databaseStorage.findBotByToken(token);
        if (bot == null) {
            throw AuthorizationBadTokenException.create();
        }
        return new BotObject(bot, databaseStorage, storage);
    }

    @Override
    public AbstractBot botById(int id) {
        BotEntity bot = databaseStorage.findBotById(id);
        if (bot == null) {
            throw new IllegalArgumentException("Bot with given bot id is not exist");
        }
        return new BotObject(bot, databaseStorage, storage);
    }
}

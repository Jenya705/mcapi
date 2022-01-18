package com.github.jenya705.mcapi.server.module.bot;

import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.module.config.ConfigModule;
import com.github.jenya705.mcapi.server.module.database.DatabaseModule;
import com.github.jenya705.mcapi.server.util.PatternUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.UUID;

/**
 * @author Jenya705
 */
@Singleton
public class BotManagementImpl extends AbstractApplicationModule implements BotManagement {

    private final DatabaseModule databaseModule;
    private final BotManagementConfig config;

    @Inject
    public BotManagementImpl(ServerApplication application, DatabaseModule databaseModule, ConfigModule configModule) {
        super(application);
        this.databaseModule = databaseModule;
        config = new BotManagementConfig(
                configModule
                        .getConfig()
                        .required("bot")
        );
    }

    @Override
    public boolean addBot(String name, UUID owner, String token) {
        if (!validateBotName(name)) return false;
        databaseModule
                .storage()
                .save(BotEntity
                        .builder()
                        .name(name)
                        .token(token)
                        .owner(owner)
                        .build()
                );
        return true;
    }

    private boolean validateBotName(String name) {
        return PatternUtils.validateAllString(config.getNamePattern(), name);
    }

}

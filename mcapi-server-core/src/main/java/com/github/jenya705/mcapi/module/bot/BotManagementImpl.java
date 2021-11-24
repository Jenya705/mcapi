package com.github.jenya705.mcapi.module.bot;

import com.github.jenya705.mcapi.AbstractApplicationModule;
import com.github.jenya705.mcapi.Bean;
import com.github.jenya705.mcapi.OnStartup;
import com.github.jenya705.mcapi.entity.BotEntity;
import com.github.jenya705.mcapi.module.config.ConfigModule;
import com.github.jenya705.mcapi.module.database.DatabaseModule;
import com.github.jenya705.mcapi.util.PatternUtils;

import java.util.UUID;

/**
 * @author Jenya705
 */
public class BotManagementImpl extends AbstractApplicationModule implements BotManagement {

    @Bean
    private DatabaseModule databaseModule;

    private BotManagementConfig config;

    @OnStartup
    public void start() {
        config = new BotManagementConfig(
                bean(ConfigModule.class)
                        .getConfig()
                        .required("bot")
        );
    }

    @Override
    public boolean addBot(String name, UUID owner, String token) {
        if (!validateBotName(name)) return false;
        databaseModule
                .storage()
                .update(BotEntity
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

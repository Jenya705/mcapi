package com.github.jenya705.mcapi.module.rest;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.Bean;
import com.github.jenya705.mcapi.OnStartup;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.entity.BotEntity;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import com.github.jenya705.mcapi.module.authorization.AuthorizationModule;
import com.github.jenya705.mcapi.module.database.DatabaseModule;
import com.github.jenya705.mcapi.module.mapper.Mapper;

/**
 * @author Jenya705
 */
public class RestModule implements BaseCommon {

    @Bean
    private Mapper mapper;

    @Bean
    private AuthorizationModule authorizationModule;

    @Bean
    private DatabaseModule databaseModule;

    @OnStartup
    public void start() {
        mapper
                .rawDeserializer(AbstractBot.class, authorization -> authorizationModule.bot(authorization))
                .rawDeserializer(BotEntity.class, token -> databaseModule.storage().findBotByToken(token))
                .rawDeserializer(ApiPlayer.class, id ->
                        core()
                                .getOptionalPlayerId(id)
                                .orElseThrow(() -> new PlayerNotFoundException(id))
                );
    }
}

package com.github.jenya705.mcapi.test;

import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.util.TokenUtils;
import lombok.experimental.UtilityClass;

import java.util.UUID;

/**
 * @author Jenya705
 */
@UtilityClass
public class TestUtils {

    public final BotEntity sampleBotEntity = BotEntity
            .builder()
            .owner(UUID.randomUUID())
            .token(TokenUtils.generateToken())
            .name("some")
            .id(1)
            .build();

}

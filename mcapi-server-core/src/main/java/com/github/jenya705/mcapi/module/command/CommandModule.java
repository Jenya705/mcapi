package com.github.jenya705.mcapi.module.command;

import com.github.jenya705.mcapi.command.ApiCommand;
import com.github.jenya705.mcapi.entity.BotEntity;

/**
 * @author Jenya705
 */
public interface CommandModule {

    void registerCommand(ApiCommand command, BotEntity owner);
}

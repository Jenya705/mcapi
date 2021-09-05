package com.github.jenya705.mcapi.module.command;

import com.github.jenya705.mcapi.command.ApiCommand;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.entity.BotEntity;

/**
 * @author Jenya705
 */
public interface CommandModule {

    void registerCommand(ApiCommand command, AbstractBot owner);

    void addOptionParser(String type, CommandValueOptionParser parser);

    CommandValueOptionParser getParser(String type);

}

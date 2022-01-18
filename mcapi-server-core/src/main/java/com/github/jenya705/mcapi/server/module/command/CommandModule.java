package com.github.jenya705.mcapi.server.module.command;

import com.github.jenya705.mcapi.command.Command;
import com.github.jenya705.mcapi.server.command.CommandExecutor;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.google.inject.ImplementedBy;

/**
 * @author Jenya705
 */
@ImplementedBy(CommandModuleImpl.class)
public interface CommandModule {

    String permissionFormat = "mcapi.bot.%s";

    void registerCommand(Command command, AbstractBot owner);

    void deleteCommand(String name, AbstractBot owner);

    void addOptionParser(String type, CommandOptionParser parser);

    CommandOptionParser getParser(String type);

    CommandExecutor getBotCommandExecutor(AbstractBot bot, String command);
}

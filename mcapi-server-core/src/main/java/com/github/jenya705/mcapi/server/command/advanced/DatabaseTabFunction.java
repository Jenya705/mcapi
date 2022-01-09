package com.github.jenya705.mcapi.server.command.advanced;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.server.command.CommandTab;
import com.github.jenya705.mcapi.server.module.database.safe.DatabaseGetter;

import java.util.List;

/**
 * @author Jenya705
 */
@FunctionalInterface
public interface DatabaseTabFunction {

    List<CommandTab> tab(CommandSender sender, String permission, DatabaseGetter databaseGetter);
}

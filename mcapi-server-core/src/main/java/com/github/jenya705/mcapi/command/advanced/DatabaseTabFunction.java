package com.github.jenya705.mcapi.command.advanced;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.command.CommandTab;
import com.github.jenya705.mcapi.module.database.safe.DatabaseGetter;

import java.util.List;

/**
 * @author Jenya705
 */
@FunctionalInterface
public interface DatabaseTabFunction {

    List<CommandTab> tab(CommandSender sender, String permission, DatabaseGetter databaseGetter);
}

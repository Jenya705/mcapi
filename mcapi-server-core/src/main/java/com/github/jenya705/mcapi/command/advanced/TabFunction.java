package com.github.jenya705.mcapi.command.advanced;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.command.CommandTab;

import java.util.List;

/**
 * @author Jenya705
 */
@FunctionalInterface
public interface TabFunction {

    List<CommandTab> apply(CommandSender sender, String permission, boolean async);
}

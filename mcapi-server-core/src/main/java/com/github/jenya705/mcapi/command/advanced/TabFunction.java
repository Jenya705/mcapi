package com.github.jenya705.mcapi.command.advanced;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.command.CommandTab;

import java.util.List;

/**
 * @author Jenya705
 */
@FunctionalInterface
public interface TabFunction {

    List<CommandTab> apply(ApiCommandSender sender, String permission, boolean async);
}

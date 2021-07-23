package com.github.jenya705.mcapi.command;

import com.github.jenya705.mcapi.ApiSender;

import java.util.Iterator;

/**
 * @since 1.0
 * @author Jenya705
 */
@FunctionalInterface
public interface ApiServerCommandExecutor {

    void execute(ApiSender sender, Iterator<String> args);

}

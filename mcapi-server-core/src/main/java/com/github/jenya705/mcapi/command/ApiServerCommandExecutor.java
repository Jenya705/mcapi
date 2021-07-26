package com.github.jenya705.mcapi.command;

import com.github.jenya705.mcapi.ApiSender;

import java.util.List;

/**
 * @since 1.0
 * @author Jenya705
 */
public interface ApiServerCommandExecutor {

    void execute(ApiSender sender, ApiServerCommandIterator<String> args);

    List<String> possibleVariants(ApiSender sender, ApiServerCommandIterator<String> args);

}

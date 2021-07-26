package com.github.jenya705.mcapi.command;

import com.github.jenya705.mcapi.ApiSender;
import com.github.jenya705.mcapi.ApiServerApplication;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @since 1.0
 * @author Jenya705
 */
public class ApiServerMainCommand implements ApiServerCommandExecutor {

    private final ApiServerCommandExecutor source;

    public ApiServerMainCommand() {
        ApiServerApplication application = ApiServerApplication.getApplication();
        ApiServerCommandFactory commandFactory = application.getCommandFactory();
        source = commandFactory.createSubContainer(
                Map.of(
                        "token", commandFactory.createSubContainer(
                                Map.of(
                                        "create", commandFactory.createCommandInstance(ApiServerCreateTokenCommand.class), // Create
                                        "list", commandFactory.createCommandInstance(ApiServerListTokenCommand.class)
                                )
                        ) // Token
                ) // End
        );
    }

    @Override
    public void execute(ApiSender sender, ApiServerCommandIterator<String> args) {
        source.execute(sender, args);
    }

    @Override
    public List<String> possibleVariants(ApiSender sender, ApiServerCommandIterator<String> args) {
        return source.possibleVariants(sender, args);
    }
}

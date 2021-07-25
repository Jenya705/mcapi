package com.github.jenya705.mcapi.command;

import com.github.jenya705.mcapi.ApiSender;
import lombok.Getter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/**
 * @since 1.0
 * @author Jenya705
 */
@Getter
public abstract class ApiServerSubCommandContainer implements ApiServerCommandExecutor {

    private final Map<String, ApiServerCommandExecutor> subCommands = new HashMap<>();

    @Override
    public void execute(ApiSender sender, Iterator<String> args) {
        if (!args.hasNext()) {
            sendHelp(sender);
            return;
        }
        String command = args.next().toLowerCase(Locale.ROOT);
        ApiServerCommandExecutor commandExecutor = subCommands.getOrDefault(command, null);
        if (commandExecutor == null) {
            sendSubCommandNotExist(sender, command);
            sendHelp(sender);
            return;
        }
        commandExecutor.execute(sender, args);
    }

    public abstract void sendSubCommandNotExist(ApiSender sender, String command);

    public abstract void sendHelp(ApiSender sender);

    public void addCommand(String name, ApiServerCommandExecutor executor) {
        subCommands.put(name.toLowerCase(Locale.ROOT), executor);
    }

}

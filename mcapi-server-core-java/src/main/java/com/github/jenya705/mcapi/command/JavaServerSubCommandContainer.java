package com.github.jenya705.mcapi.command;

import com.github.jenya705.mcapi.ApiSender;
import com.github.jenya705.mcapi.ApiServerApplication;
import com.github.jenya705.mcapi.JavaServerConfiguration;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @since 1.0
 * @author Jenya705
 */
public class JavaServerSubCommandContainer extends ApiServerSubCommandContainer {

    private static JavaServerConfiguration getConfig() {
        return (JavaServerConfiguration) ApiServerApplication.getApplication().getCore().getConfig();
    }

    public JavaServerSubCommandContainer() {}

    public JavaServerSubCommandContainer(Map<String, ApiServerCommandExecutor> subs) {
        subs.forEach(this::addCommand);
    }

    @Override
    public void sendSubCommandNotExist(ApiSender sender, String command) {
        sender.sendMessage(
                getConfig().getSubContainerCommandNotExist().replaceAll("%name%", command)
        );
    }

    @Override
    public void sendHelp(ApiSender sender) {
        sender.sendMessage(String.join(",", getSubCommands().keySet()));
    }
}

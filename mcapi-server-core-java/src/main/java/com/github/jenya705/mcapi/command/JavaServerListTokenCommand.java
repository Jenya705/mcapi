package com.github.jenya705.mcapi.command;

import com.github.jenya705.mcapi.*;
import com.github.jenya705.mcapi.token.ApiServerTokenHolderEntity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @since 1.0
 * @author Jenya705
 */
public class JavaServerListTokenCommand extends ApiServerListTokenCommand {

    protected static JavaServerConfiguration getConfig() {
        return (JavaServerConfiguration) ApiServerApplication.getApplication().getCore().getConfig();
    }

    @Override
    public void sendList(ApiSender sender, List<ApiServerTokenHolderEntity> tokenHolders) {
        if (sender instanceof JavaPlayer) {
            Component component = Component.empty();
            for (ApiServerTokenHolderEntity tokenHolder: tokenHolders) {
                component = component
                        .append(Component.text(
                                getConfig().getListTokenLayout().replaceAll("%name%", tokenHolder.getName()))
                        ).append(Component.text(
                                tokenHolder.getToken()).clickEvent(ClickEvent.suggestCommand(tokenHolder.getToken()))
                        );
            }
            ((JavaPlayer) sender).sendMessage(component);
        }
        else {
            sender.sendMessage(tokenHolders
                    .stream()
                    .map(it -> getConfig().getListTokenLayout().replaceAll("%name%", it.getName()) + it.getToken())
                    .collect(Collectors.joining("\n"))
            );
        }
    }

    @Override
    public void sendMessagePlayerNameIsNotGiven(ApiSender sender) {
        JavaServerTokenCommand.sendMessagePlayerNameIsNotGiven(sender);
    }

    @Override
    public void sendMessagePlayerIsNotExist(ApiSender sender) {
        JavaServerTokenCommand.sendMessagePlayerIsNotExist(sender);
    }
}

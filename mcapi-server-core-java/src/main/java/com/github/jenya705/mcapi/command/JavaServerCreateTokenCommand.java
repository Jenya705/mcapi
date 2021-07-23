package com.github.jenya705.mcapi.command;

import com.github.jenya705.mcapi.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;

/**
 * @since 1.0
 * @author Jenya705
 */
public class JavaServerCreateTokenCommand extends ApiServerCreateTokenCommand {

    protected static JavaServerConfiguration getConfig() {
        return (JavaServerConfiguration) ApiServerApplication.getApplication().getCore().getConfig();
    }

    @Override
    public void sendMessagePlayerNameIsNotGiven(ApiSender sender) {
        sender.sendMessage(getConfig().getCreateTokenPlayerNameIsNotGivenMessage());
    }

    @Override
    public void sendMessagePlayerIsNotExist(ApiSender sender) {
        sender.sendMessage(getConfig().getCreateTokenPlayerIsNotExistMessage());
    }

    @Override
    public void sendMessageTokenNameIsNotGiven(ApiSender sender) {
        sender.sendMessage(getConfig().getCreateTokenNameIsNotGivenMessage());
    }

    @Override
    public void sendMessageSuccess(ApiSender sender, String token, String name) {
        if (sender instanceof JavaPlayer) {
            ((JavaPlayer) sender).sendMessage(
                    Component
                            .text(
                                    getConfig().getCreateTokenSuccess()
                                            .replaceAll("%name%", name)
                            )
                            .append(Component.text(
                                            token
                                    ).clickEvent(ClickEvent.suggestCommand(
                                            token
                                    ))
                            )
            );
            return;
        }
        sender.sendMessage(getConfig().getCreateTokenSuccess().replaceAll("%name%", name) + token);
    }
}

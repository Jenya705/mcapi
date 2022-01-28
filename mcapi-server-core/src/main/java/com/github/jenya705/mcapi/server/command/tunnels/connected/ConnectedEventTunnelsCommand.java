package com.github.jenya705.mcapi.server.command.tunnels.connected;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.command.RootCommand;
import com.github.jenya705.mcapi.server.command.advanced.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.server.module.config.message.MessageContainer;
import com.google.inject.Inject;

import java.util.Collections;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
public class ConnectedEventTunnelsCommand extends AdvancedCommandExecutor<ConnectedEventTunnelsArguments> {

    @Inject
    public ConnectedEventTunnelsCommand(ServerApplication application, MessageContainer messageContainer) {
        super(application, messageContainer, ConnectedEventTunnelsArguments.class);
        this
                .tab(() -> Collections.singletonList("<page>"));
    }

    @Override
    public void onCommand(CommandSender sender, ConnectedEventTunnelsArguments args, String permission) {
        sendMessage(
                sender,
                messageContainer().eventTunnelList(
                        eventTunnel()
                                .getClients()
                                .stream()
                                .skip((long) args.getPage() * RootCommand.maxListElements)
                                .limit(RootCommand.maxListElements)
                                .collect(Collectors.toList()),
                        args.getPage() + 1
                )
        );
    }
}

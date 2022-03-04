package com.github.jenya705.mcapi.server.command.tunnels.subscriptions;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.command.AdditionalPermissions;
import com.github.jenya705.mcapi.server.command.RootCommand;
import com.github.jenya705.mcapi.server.command.advanced.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.module.config.message.MessageContainer;
import com.github.jenya705.mcapi.server.module.database.storage.EventDatabaseStorage;
import com.github.jenya705.mcapi.server.module.web.tunnel.EventTunnelClient;
import com.github.jenya705.mcapi.server.util.ListUtils;
import com.google.inject.Inject;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@AdditionalPermissions("others")
public class SubscriptionsEventTunnelsCommand extends AdvancedCommandExecutor<SubscriptionsEventTunnelsArguments> {

    private final EventDatabaseStorage databaseStorage;

    @Inject
    public SubscriptionsEventTunnelsCommand(ServerApplication application, MessageContainer messageContainer,
                                            EventDatabaseStorage databaseStorage) {
        super(application, messageContainer, SubscriptionsEventTunnelsArguments.class);
        this.databaseStorage = databaseStorage;
        this
                .tab(() -> Collections.singletonList("<token>"))
                .tab(() -> Collections.singletonList("<page>"));
    }

    @Override
    public void onCommand(CommandSender sender, SubscriptionsEventTunnelsArguments args, String permission) {
        BotEntity bot = databaseStorage.findBotByToken(args.getToken());
        UUID executorUuid = sender instanceof Player ? ((Player) sender).getUuid() : null;
        if (bot == null ||
                !(bot.getOwner().equals(executorUuid) || hasPermission(sender, permission, "others"))
        ) {
            sendMessage(sender, messageContainer().notPermitted());
            return;
        }
        List<Collection<String>> connectionSubscriptions =
                eventTunnel()
                        .getClients()
                        .stream()
                        .filter(it -> it.getOwner().getEntity().getId() == bot.getId())
                        .map(EventTunnelClient::getSubscriptions)
                        .collect(Collectors.toList());
        if (connectionSubscriptions.isEmpty()) {
            sendMessage(sender, messageContainer().notConnectedToGateway());
            return;
        }
        sendMessage(
                sender,
                messageContainer().subscriptionList(
                        ListUtils.joinCollection(connectionSubscriptions)
                                .stream()
                                .skip((long) RootCommand.maxListElements * args.getPage())
                                .limit(RootCommand.maxListElements)
                                .collect(Collectors.toList()),
                        bot.getName(),
                        args.getPage() + 1
                )
        );
    }
}

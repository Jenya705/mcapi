package com.github.jenya705.mcapi.server.command.tunnels.subscriptions;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.command.AdditionalPermissions;
import com.github.jenya705.mcapi.server.command.RootCommand;
import com.github.jenya705.mcapi.server.command.advanced.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.module.config.message.MessageContainer;
import com.github.jenya705.mcapi.server.module.database.DatabaseModule;
import com.github.jenya705.mcapi.server.module.web.tunnel.EventTunnelClient;
import com.google.inject.Inject;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@AdditionalPermissions("others")
public class SubscriptionsEventTunnelsCommand extends AdvancedCommandExecutor<SubscriptionsEventTunnelsArguments> {

    private final DatabaseModule databaseModule;

    @Inject
    public SubscriptionsEventTunnelsCommand(ServerApplication application, MessageContainer messageContainer, DatabaseModule databaseModule) {
        super(application, messageContainer, SubscriptionsEventTunnelsArguments.class);
        this.databaseModule = databaseModule;
        this
                .tab(() -> Collections.singletonList("<token>"))
                .tab(() -> Collections.singletonList("<page>"));
    }

    @Override
    public void onCommand(CommandSender sender, SubscriptionsEventTunnelsArguments args, String permission) {
        BotEntity bot = databaseModule
                .storage()
                .findBotByToken(args.getToken());
        UUID executorUuid = sender instanceof Player ? ((Player) sender).getUuid() : null;
        if (bot == null || (
                !bot.getOwner().equals(executorUuid) && !hasPermission(sender, permission, "others"))) {
            sendMessage(sender, messageContainer().notPermitted());
            return;
        }
        Collection<String> subscriptions =
                eventTunnel()
                        .getClients()
                        .stream()
                        .filter(it -> it.getOwner().getEntity().getId() == bot.getId())
                        .findFirst()
                        .map(EventTunnelClient::getSubscriptions)
                        .orElse(null);
        if (subscriptions == null) {
            sendMessage(sender, messageContainer().notConnectedToGateway());
            return;
        }
        sendMessage(
                sender,
                messageContainer().subscriptionList(
                        subscriptions
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

package com.github.jenya705.mcapi.server.command.tunnels.subscriptions;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.command.AdditionalPermissions;
import com.github.jenya705.mcapi.server.command.advanced.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.module.database.DatabaseModule;
import com.github.jenya705.mcapi.server.module.web.tunnel.EventTunnelClient;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

/**
 * @author Jenya705
 */
@AdditionalPermissions("others")
public class SubscriptionsEventTunnelsCommand extends AdvancedCommandExecutor<SubscriptionsEventTunnelsArguments> {

    private final DatabaseModule databaseModule;

    private SubscriptionsEventTunnelsConfig config;

    @Inject
    public SubscriptionsEventTunnelsCommand(ServerApplication application, DatabaseModule databaseModule) {
        super(application, SubscriptionsEventTunnelsArguments.class);
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
            sendMessage(sender, config.getNotPermitted());
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
            sendMessage(sender, config.getBotIsNotConnected());
            return;
        }
        sendListMessage(
                sender,
                config.getListLayout(),
                config.getListElement(),
                config.getListDelimiter(),
                new ArrayList<>(subscriptions),
                str -> new String[]{
                        "%name%", str
                },
                config.getMaxElements(),
                args.getPage(),
                "%name%", bot.getName(),
                "%page%", Integer.toString(args.getPage() + 1)
        );
    }

    @Override
    public void setConfig(ConfigData config) {
        this.config = new SubscriptionsEventTunnelsConfig(config);
        setConfig(this.config);
    }
}

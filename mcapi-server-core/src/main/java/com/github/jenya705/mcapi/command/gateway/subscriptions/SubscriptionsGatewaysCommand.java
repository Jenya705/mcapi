package com.github.jenya705.mcapi.command.gateway.subscriptions;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.Player;
import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.command.AdditionalPermissions;
import com.github.jenya705.mcapi.command.advanced.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.entity.BotEntity;
import com.github.jenya705.mcapi.module.database.DatabaseModule;
import com.github.jenya705.mcapi.module.web.gateway.GatewayClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

/**
 * @author Jenya705
 */
@AdditionalPermissions("others")
public class SubscriptionsGatewaysCommand extends AdvancedCommandExecutor<SubscriptionsGatewaysArguments> {

    private final DatabaseModule databaseModule = bean(DatabaseModule.class);

    private SubscriptionsGatewaysConfig config;

    public SubscriptionsGatewaysCommand(ServerApplication application) {
        super(application, SubscriptionsGatewaysArguments.class);
        this
                .tab(() -> Collections.singletonList("<token>"))
                .tab(() -> Collections.singletonList("<page>"));
    }

    @Override
    public void onCommand(CommandSender sender, SubscriptionsGatewaysArguments args, String permission) {
        BotEntity bot = databaseModule
                .storage()
                .findBotByToken(args.getToken());
        UUID executorUuid = sender instanceof Player ? ((Player) sender).getUuid() : null;
        if (!bot.getOwner().equals(executorUuid) && !hasPermission(sender, permission, "others")) {
            sendMessage(sender, config.getNotPermitted());
            return;
        }
        Collection<String> subscriptions =
                gateway()
                        .getClients()
                        .stream()
                        .filter(it -> it.getOwner().getEntity().getId() == bot.getId())
                        .findFirst()
                        .map(GatewayClient::getSubscriptions)
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
        this.config = new SubscriptionsGatewaysConfig(config);
        setConfig(this.config);
    }
}

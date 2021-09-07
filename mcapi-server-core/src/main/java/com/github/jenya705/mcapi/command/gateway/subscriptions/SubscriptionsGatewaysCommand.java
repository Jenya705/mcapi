package com.github.jenya705.mcapi.command.gateway.subscriptions;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.command.AdditionalPermissions;
import com.github.jenya705.mcapi.command.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.entity.BotEntity;
import com.github.jenya705.mcapi.gateway.GatewayClientImpl;
import com.github.jenya705.mcapi.module.database.DatabaseModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

/**
 * @author Jenya705
 */
@AdditionalPermissions("others")
public class SubscriptionsGatewaysCommand extends AdvancedCommandExecutor<SubscriptionsGatewaysArguments> implements BaseCommon {

    private final DatabaseModule databaseModule = bean(DatabaseModule.class);

    private SubscriptionsGatewaysConfig config;

    public SubscriptionsGatewaysCommand() {
        super(SubscriptionsGatewaysArguments.class);
        this
                .tab(() -> Collections.singletonList("<token>"))
                .tab(() -> Collections.singletonList("<page>"));
    }

    @Override
    public void onCommand(ApiCommandSender sender, SubscriptionsGatewaysArguments args, String permission) {
        BotEntity bot = databaseModule
                .storage()
                .findBotByToken(args.getToken());
        UUID executorUuid = sender instanceof ApiPlayer ? ((ApiPlayer) sender).getUuid() : null;
        if (!bot.getOwner().equals(executorUuid) && !hasPermission(sender, permission, "others")) {
            sendMessage(sender, config.getNotPermitted());
            return;
        }
        Set<String> subscriptions =
                app()
                        .getGateway()
                        .getClients()
                        .stream()
                        .filter(it -> it.getEntity().getId() == bot.getId())
                        .findFirst()
                        .map(GatewayClientImpl::getSubscriptions)
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

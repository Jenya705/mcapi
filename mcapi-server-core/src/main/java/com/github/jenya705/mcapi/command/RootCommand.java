package com.github.jenya705.mcapi.command;

import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.command.bot.create.CreateBotCommand;
import com.github.jenya705.mcapi.command.bot.list.ListBotCommand;
import com.github.jenya705.mcapi.command.gateway.connected.ConnectedGatewaysCommand;
import com.github.jenya705.mcapi.command.gateway.subscriptions.SubscriptionsGatewaysCommand;
import com.github.jenya705.mcapi.command.link.LinkEndCommand;
import com.github.jenya705.mcapi.command.link.LinkTogglePermissionCommand;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.data.GlobalConfigData;
import lombok.SneakyThrows;

import java.util.function.Supplier;

/**
 * @author Jenya705
 */
public class RootCommand implements Supplier<ContainerCommandExecutor>, BaseCommon {

    public static final String name = "mcapi";
    public static final String permission = "mcapi.command";

    @SneakyThrows
    @Override
    public ContainerCommandExecutor get() {
        ContainerCommandExecutor container = new ContainerCommandExecutor("mcapi.command", "mcapi");
        container
                .tree()
                .branch("bot", branch -> branch
                        .leaf("create", new CreateBotCommand())
                        .leaf("list", new ListBotCommand())
                )
                .branch("gateway", branch -> branch
                        .leaf("connected", new ConnectedGatewaysCommand())
                        .leaf("subscriptions", new SubscriptionsGatewaysCommand())
                )
                .ghostBranch("link", branch -> branch
                        .leaf("end", new LinkEndCommand())
                        .leaf("toggle", new LinkTogglePermissionCommand())
                )
        ;
        ConfigData configData =
                new GlobalConfigData(core().loadConfig("commands"));
        container.setConfig(configData);
        core().saveConfig("commands", configData.primitiveRepresent());
        return container;
    }
}

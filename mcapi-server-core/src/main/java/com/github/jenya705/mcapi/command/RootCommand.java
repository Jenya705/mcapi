package com.github.jenya705.mcapi.command;

import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.command.bot.create.CreateBotCommand;
import com.github.jenya705.mcapi.command.bot.delete.DeleteBotCommand;
import com.github.jenya705.mcapi.command.bot.list.ListBotCommand;
import com.github.jenya705.mcapi.command.gateway.connected.ConnectedGatewaysCommand;
import com.github.jenya705.mcapi.command.gateway.subscriptions.SubscriptionsGatewaysCommand;
import com.github.jenya705.mcapi.command.link.links.LinksCommand;
import com.github.jenya705.mcapi.command.linkmenu.LinkEndCommand;
import com.github.jenya705.mcapi.command.linkmenu.LinkPermissionCommand;
import com.github.jenya705.mcapi.command.linkmenu.LinkTogglePermissionCommand;
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
                        .leaf("delete", new DeleteBotCommand())
                )
                .branch("gateway", branch -> branch
                        .leaf("connected", new ConnectedGatewaysCommand())
                        .leaf("subscriptions", new SubscriptionsGatewaysCommand())
                )
                .ghostBranch("linkMenu", branch -> branch
                        .leaf("end", new LinkEndCommand())
                        .leaf("toggle", new LinkTogglePermissionCommand())
                        .leaf("permission", new LinkPermissionCommand())
                )
                .leaf("links", new LinksCommand())
        ;
        ConfigData configData =
                new GlobalConfigData(core().loadConfig("commands"));
        container.setConfig(configData);
        core().saveConfig("commands", configData.primitiveRepresent());
        return container;
    }
}

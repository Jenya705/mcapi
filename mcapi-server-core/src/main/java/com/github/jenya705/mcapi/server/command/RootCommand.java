package com.github.jenya705.mcapi.server.command;

import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.command.bot.create.CreateBotCommand;
import com.github.jenya705.mcapi.server.command.bot.delete.DeleteBotCommand;
import com.github.jenya705.mcapi.server.command.bot.list.ListBotCommand;
import com.github.jenya705.mcapi.server.command.bot.permission.block.PermissionBlockBotCommand;
import com.github.jenya705.mcapi.server.command.bot.permission.give.PermissionGiveBotCommand;
import com.github.jenya705.mcapi.server.command.bot.permission.list.PermissionListBotCommand;
import com.github.jenya705.mcapi.server.command.link.links.LinksCommand;
import com.github.jenya705.mcapi.server.command.link.unlink.UnlinkCommand;
import com.github.jenya705.mcapi.server.command.linkmenu.LinkEndCommand;
import com.github.jenya705.mcapi.server.command.linkmenu.LinkPermissionCommand;
import com.github.jenya705.mcapi.server.command.linkmenu.LinkTogglePermissionCommand;
import com.github.jenya705.mcapi.server.command.linkmenu.LinkUnlinkCommand;
import com.github.jenya705.mcapi.server.command.tunnels.connected.ConnectedEventTunnelsCommand;
import com.github.jenya705.mcapi.server.command.tunnels.subscriptions.SubscriptionsEventTunnelsCommand;
import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.module.config.ConfigModule;
import lombok.SneakyThrows;

import java.util.function.Supplier;

/**
 * @author Jenya705
 */
public class RootCommand extends AbstractApplicationModule implements Supplier<ContainerCommandExecutor> {

    public static final int maxListElements = 10;

    public static final String name = "mcapi";
    public static final String permission = "mcapi.command";

    public RootCommand(ServerApplication application) {
        super(application);
    }

    @SneakyThrows
    @Override
    public ContainerCommandExecutor get() {
        ContainerCommandExecutor container = new ContainerCommandExecutor(app(), permission, name);
        container
                .tree()
                .branch("bot", branch -> branch
                        .leaf("create", bean(CreateBotCommand.class))
                        .leaf("list", bean(ListBotCommand.class))
                        .leaf("delete", bean(DeleteBotCommand.class))
                        .branch("permission", permissionBranch -> permissionBranch
                                .leaf("list", bean(PermissionListBotCommand.class))
                                .leaf("give", bean(PermissionGiveBotCommand.class))
                                .leaf("block", bean(PermissionBlockBotCommand.class))
                        )
                )
                .branch("tunnel", branch -> branch
                        .leaf("connected", bean(ConnectedEventTunnelsCommand.class))
                        .leaf("subscriptions", bean(SubscriptionsEventTunnelsCommand.class))
                )
                .ghostBranch("linkMenu", branch -> branch
                        .leaf("end", bean(LinkEndCommand.class))
                        .leaf("toggle", bean(LinkTogglePermissionCommand.class))
                        .leaf("permission", bean(LinkPermissionCommand.class))
                        .leaf("unlink", bean(LinkUnlinkCommand.class))
                )
                .leaf("links", bean(LinksCommand.class))
                .leaf("unlink", bean(UnlinkCommand.class))
        ;
        ConfigData configData = bean(ConfigModule.class)
                .createConfig(core().loadConfig("commands"));
        container.setConfig(configData);
        core().saveConfig("commands", configData.primitiveRepresent());
        return container;
    }
}

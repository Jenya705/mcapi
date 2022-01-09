package com.github.jenya705.mcapi.server.command;

import com.github.jenya705.mcapi.server.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.ServerApplication;
import com.github.jenya705.mcapi.server.command.bot.create.CreateBotCommand;
import com.github.jenya705.mcapi.server.command.bot.delete.DeleteBotCommand;
import com.github.jenya705.mcapi.server.command.bot.list.ListBotCommand;
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
                        .leaf("create", new CreateBotCommand(app()))
                        .leaf("list", new ListBotCommand(app()))
                        .leaf("delete", new DeleteBotCommand(app()))
                        .branch("permission", permissionBranch -> permissionBranch
                                .leaf("list", new PermissionListBotCommand(app()))
                                .leaf("give", new PermissionGiveBotCommand(app()))
                        )
                )
                .branch("tunnel", branch -> branch
                        .leaf("connected", new ConnectedEventTunnelsCommand(app()))
                        .leaf("subscriptions", new SubscriptionsEventTunnelsCommand(app()))
                )
                .ghostBranch("linkMenu", branch -> branch
                        .leaf("end", new LinkEndCommand(app()))
                        .leaf("toggle", new LinkTogglePermissionCommand(app()))
                        .leaf("permission", new LinkPermissionCommand(app()))
                        .leaf("unlink", new LinkUnlinkCommand(app()))
                )
                .leaf("links", new LinksCommand(app()))
                .leaf("unlink", new UnlinkCommand(app()))
        ;
        ConfigData configData = bean(ConfigModule.class)
                .createConfig(core().loadConfig("commands"));
        container.setConfig(configData);
        core().saveConfig("commands", configData.primitiveRepresent());
        return container;
    }
}

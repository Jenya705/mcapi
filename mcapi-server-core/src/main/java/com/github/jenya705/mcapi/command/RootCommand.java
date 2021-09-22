package com.github.jenya705.mcapi.command;

import com.github.jenya705.mcapi.AbstractApplicationModule;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.command.bot.create.CreateBotCommand;
import com.github.jenya705.mcapi.command.bot.delete.DeleteBotCommand;
import com.github.jenya705.mcapi.command.bot.list.ListBotCommand;
import com.github.jenya705.mcapi.command.gateway.connected.ConnectedGatewaysCommand;
import com.github.jenya705.mcapi.command.gateway.subscriptions.SubscriptionsGatewaysCommand;
import com.github.jenya705.mcapi.command.link.links.LinksCommand;
import com.github.jenya705.mcapi.command.link.unlink.UnlinkCommand;
import com.github.jenya705.mcapi.command.linkmenu.LinkEndCommand;
import com.github.jenya705.mcapi.command.linkmenu.LinkPermissionCommand;
import com.github.jenya705.mcapi.command.linkmenu.LinkTogglePermissionCommand;
import com.github.jenya705.mcapi.command.linkmenu.LinkUnlinkCommand;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.data.GlobalConfigData;
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
        ContainerCommandExecutor container = new ContainerCommandExecutor(app(), "mcapi.command", "mcapi");
        container
                .tree()
                .branch("bot", branch -> branch
                        .leaf("create", new CreateBotCommand(app()))
                        .leaf("list", new ListBotCommand(app()))
                        .leaf("delete", new DeleteBotCommand(app()))
                )
                .branch("gateway", branch -> branch
                        .leaf("connected", new ConnectedGatewaysCommand(app()))
                        .leaf("subscriptions", new SubscriptionsGatewaysCommand(app()))
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
        ConfigData configData =
                new GlobalConfigData(core().loadConfig("commands"));
        container.setConfig(configData);
        core().saveConfig("commands", configData.primitiveRepresent());
        return container;
    }
}

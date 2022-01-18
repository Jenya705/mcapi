package com.github.jenya705.mcapi.server.command.linkmenu;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.application.BaseCommon;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.command.MenuCommand;
import com.github.jenya705.mcapi.server.command.NoConfig;
import com.github.jenya705.mcapi.server.module.link.LinkingModule;
import com.github.jenya705.mcapi.server.stringful.StringfulIterator;
import com.google.inject.Inject;

/**
 * @author Jenya705
 */
@NoConfig
public class LinkTogglePermissionCommand extends MenuCommand implements BaseCommon {

    private final LinkingModule linkingModule;

    private final ServerApplication application;

    @Override
    public ServerApplication app() {
        return application;
    }

    @Inject
    public LinkTogglePermissionCommand(ServerApplication application, LinkingModule linkingModule) {
        super(application);
        this.application = application;
        this.linkingModule = linkingModule;
    }

    @Override
    public void menuCommand(CommandSender sender, StringfulIterator args, String permission) throws Exception {
        if (!args.hasNext(2) || !(sender instanceof Player)) return;
        Player player = (Player) sender;
        int id = Integer.parseInt(args.next());
        String linkPermission = args.next();
        linkingModule.toggle(player, id, linkPermission);
        linkingModule.update(player, id);
    }
}

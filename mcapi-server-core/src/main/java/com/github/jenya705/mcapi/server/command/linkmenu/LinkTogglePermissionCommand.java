package com.github.jenya705.mcapi.server.command.linkmenu;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.BaseCommon;
import com.github.jenya705.mcapi.server.ServerApplication;
import com.github.jenya705.mcapi.server.command.MenuCommand;
import com.github.jenya705.mcapi.server.command.NoConfig;
import com.github.jenya705.mcapi.server.module.link.LinkingModule;
import com.github.jenya705.mcapi.server.stringful.StringfulIterator;

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

    public LinkTogglePermissionCommand(ServerApplication application) {
        super(application);
        this.application = application;
        linkingModule = bean(LinkingModule.class);
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

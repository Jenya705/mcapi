package com.github.jenya705.mcapi.command.linkmenu;

import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.Player;
import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.command.MenuCommand;
import com.github.jenya705.mcapi.command.NoConfig;
import com.github.jenya705.mcapi.module.link.LinkingModule;
import com.github.jenya705.mcapi.stringful.StringfulIterator;

/**
 * @author Jenya705
 */
@NoConfig
public class LinkEndCommand extends MenuCommand implements BaseCommon {

    private final LinkingModule linkingModule;

    private final ServerApplication application;

    @Override
    public ServerApplication app() {
        return application;
    }

    public LinkEndCommand(ServerApplication application) {
        this.application = application;
        linkingModule = bean(LinkingModule.class);
    }

    @Override
    public void menuCommand(CommandSender sender, StringfulIterator args, String permission) throws Exception {
        if (!args.hasNext(1) && !(sender instanceof Player)) return;
        Player player = (Player) sender;
        int id = Integer.parseInt(args.next());
        boolean enabled = Boolean.parseBoolean(args.next());
        linkingModule.end(player, id, enabled);
    }
}

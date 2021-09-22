package com.github.jenya705.mcapi.command.linkmenu;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.command.MenuCommand;
import com.github.jenya705.mcapi.command.NoConfig;
import com.github.jenya705.mcapi.module.link.LinkingModule;
import com.github.jenya705.mcapi.stringful.StringfulIterator;

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
        this.application = application;
        linkingModule = bean(LinkingModule.class);
    }

    @Override
    public void menuCommand(ApiCommandSender sender, StringfulIterator args, String permission) throws Exception {
        if (!args.hasNext(2) || !(sender instanceof ApiPlayer)) return;
        ApiPlayer player = (ApiPlayer) sender;
        int id = Integer.parseInt(args.next());
        String linkPermission = args.next();
        linkingModule.toggle(player, id, linkPermission);
        linkingModule.update(player, id);
    }
}

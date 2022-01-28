package com.github.jenya705.mcapi.server.command.linkmenu;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.application.BaseCommon;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.command.CommandsUtils;
import com.github.jenya705.mcapi.server.command.MenuCommand;
import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.data.loadable.Value;
import com.github.jenya705.mcapi.server.module.config.Config;
import com.github.jenya705.mcapi.server.module.config.message.MessageContainer;
import com.github.jenya705.mcapi.server.module.link.LinkingModule;
import com.github.jenya705.mcapi.server.stringful.StringfulIterator;
import com.google.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jenya705
 */
public class LinkUnlinkCommand extends MenuCommand implements BaseCommon {

    private final MessageContainer messageContainer;
    private final LinkingModule linkingModule;

    private final ServerApplication application;

    @Override
    public ServerApplication app() {
        return application;
    }

    @Inject
    public LinkUnlinkCommand(ServerApplication application, LinkingModule linkingModule, MessageContainer messageContainer) {
        super(application);
        this.application = application;
        this.linkingModule = linkingModule;
        this.messageContainer = messageContainer;
    }

    @Override
    public void menuCommand(CommandSender sender, StringfulIterator args, String permission) throws Exception {
        if (!args.hasNext(1) || !(sender instanceof Player)) {
            return;
        }
        Player player = (Player) sender;
        int botId = Integer.parseInt(args.next());
        linkingModule.unlink(botId, player);
        player.sendMessage(messageContainer.render(messageContainer.success(), player));
    }
}

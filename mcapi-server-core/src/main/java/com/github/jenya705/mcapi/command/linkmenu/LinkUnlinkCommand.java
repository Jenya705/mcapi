package com.github.jenya705.mcapi.command.linkmenu;

import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.Player;
import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.command.CommandsUtils;
import com.github.jenya705.mcapi.command.MenuCommand;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.data.loadable.Value;
import com.github.jenya705.mcapi.module.config.Config;
import com.github.jenya705.mcapi.module.link.LinkingModule;
import com.github.jenya705.mcapi.stringful.StringfulIterator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jenya705
 */
public class LinkUnlinkCommand extends MenuCommand implements BaseCommon {

    @Getter
    @Setter
    @ToString
    static class CommandConfig extends Config {
        @Value
        private String success = "&cUnlinked!";

        public CommandConfig(ConfigData configData) {
            load(configData);
        }
    }

    private CommandConfig config;
    private LinkingModule linkingModule;

    private final ServerApplication application;

    @Override
    public ServerApplication app() {
        return application;
    }

    public LinkUnlinkCommand(ServerApplication application) {
        this.application = application;
        autoBeans();
    }

    @Override
    public void menuCommand(CommandSender sender, StringfulIterator args, String permission) throws Exception {
        if (!args.hasNext(1) || !(sender instanceof Player)) {
            return;
        }
        Player player = (Player) sender;
        int botId = Integer.parseInt(args.next());
        linkingModule.unlink(botId, player);
        player.sendMessage(CommandsUtils.placeholderMessage(config.getSuccess()));
    }

    @Override
    public void setConfig(ConfigData config) {
        this.config = new CommandConfig(config);
    }
}

package com.github.jenya705.mcapi.command.linkmenu;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.command.CommandsUtils;
import com.github.jenya705.mcapi.command.MenuCommand;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.entity.BotLinkEntity;
import com.github.jenya705.mcapi.module.config.Config;
import com.github.jenya705.mcapi.module.config.Value;
import com.github.jenya705.mcapi.module.database.DatabaseModule;
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

    private final DatabaseModule databaseModule = bean(DatabaseModule.class);

    @Override
    public void menuCommand(ApiCommandSender sender, StringfulIterator args, String permission) throws Exception {
        if (!args.hasNext(1) || !(sender instanceof ApiPlayer)) {
            return;
        }
        ApiPlayer player = (ApiPlayer) sender;
        int botId = Integer.parseInt(args.next());
        DatabaseModule.async.submit(() -> {
            databaseModule
                    .storage()
                    .delete(new BotLinkEntity(
                            botId, player.getUuid()
                    ));
            player.sendMessage(CommandsUtils.placeholderMessage(config.getSuccess()));
        });
    }

    @Override
    public void setConfig(ConfigData config) {
        this.config = new CommandConfig(config);
    }
}

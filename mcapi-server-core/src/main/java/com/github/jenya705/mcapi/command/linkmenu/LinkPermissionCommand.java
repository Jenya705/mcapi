package com.github.jenya705.mcapi.command.linkmenu;

import com.github.jenya705.mcapi.*;
import com.github.jenya705.mcapi.command.CommandsUtils;
import com.github.jenya705.mcapi.command.MenuCommand;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.data.loadable.Global;
import com.github.jenya705.mcapi.data.loadable.Value;
import com.github.jenya705.mcapi.module.config.Config;
import com.github.jenya705.mcapi.module.database.DatabaseModule;
import com.github.jenya705.mcapi.module.localization.LocalizationModule;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.stringful.StringfulIterator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jenya705
 */
public class LinkPermissionCommand extends MenuCommand implements BaseCommon {

    @Getter
    @Setter
    @ToString
    static class CommandConfig extends Config {

        @Value
        private String listLayout = "&ePermissions for %name%\n%list%";

        @Value
        @Global("localizedPermissionElementListRepresentation")
        private String listElement = "&7- &e%permission%";

        @Value
        @Global("listsDelimiter")
        private String listDelimiter = "&r\n";

        public CommandConfig(ConfigData configData) {
            load(configData);
        }
    }

    @Bean
    private DatabaseModule databaseModule;
    @Bean
    private LocalizationModule localizationModule;
    private CommandConfig config;

    private final ServerApplication application;

    @Override
    public ServerApplication app() {
        return application;
    }

    public LinkPermissionCommand(ServerApplication application) {
        this.application = application;
        autoBeans();
    }

    @Override
    public void menuCommand(CommandSender sender, StringfulIterator args, String permission) throws Exception {
        if (!args.hasNext(1) || !(sender instanceof Player)) return;
        Player player = (Player) sender;
        int botId = Integer.parseInt(args.next());
        DatabaseModule.async.submit(() ->
                player.sendMessage(
                        CommandsUtils.listMessage(
                                config.getListLayout(),
                                config.getListElement(),
                                config.getListDelimiter(),
                                databaseModule
                                        .storage()
                                        .findPermissionsByIdAndTarget(botId, player.getUuid()),
                                it -> new String[]{
                                        "%permission%",
                                        localizationModule
                                                .getLinkPermissionLocalization(it.getPermission())
                                },
                                "%name%", databaseModule
                                        .storage()
                                        .findBotById(botId)
                                        .getName()
                        )
                )
        );
    }

    @Override
    public void setConfig(ConfigData config) {
        this.config = new CommandConfig(config);
    }
}

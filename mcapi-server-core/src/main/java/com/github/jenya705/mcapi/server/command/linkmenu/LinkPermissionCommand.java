package com.github.jenya705.mcapi.server.command.linkmenu;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.application.BaseCommon;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.command.CommandsUtils;
import com.github.jenya705.mcapi.server.command.MenuCommand;
import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.data.loadable.Global;
import com.github.jenya705.mcapi.server.data.loadable.Value;
import com.github.jenya705.mcapi.server.module.config.Config;
import com.github.jenya705.mcapi.server.module.database.DatabaseModule;
import com.github.jenya705.mcapi.server.module.localization.LocalizationModule;
import com.github.jenya705.mcapi.server.stringful.StringfulIterator;
import com.google.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.stream.Collectors;

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

    private final DatabaseModule databaseModule;
    private final LocalizationModule localizationModule;
    private final ServerApplication application;

    private CommandConfig config;

    @Override
    public ServerApplication app() {
        return application;
    }

    @Inject
    public LinkPermissionCommand(ServerApplication application, DatabaseModule databaseModule, LocalizationModule localizationModule) {
        super(application);
        this.application = application;
        this.databaseModule = databaseModule;
        this.localizationModule = localizationModule;
    }

    @Override
    public void menuCommand(CommandSender sender, StringfulIterator args, String permission) throws Exception {
        if (!args.hasNext(1) || !(sender instanceof Player)) return;
        Player player = (Player) sender;
        int botId = Integer.parseInt(args.next());
        worker().invoke(() ->
                player.sendMessage(
                        CommandsUtils.listMessage(
                                config.getListLayout(),
                                config.getListElement(),
                                config.getListDelimiter(),
                                databaseModule
                                        .storage()
                                        .findPermissionsByIdAndTarget(botId, player.getUuid())
                                        .stream()
                                        .filter(it -> !it.isRegex())
                                        .collect(Collectors.toList()),
                                it -> new String[]{
                                        "%permission%",
                                        localizationModule
                                                .getLinkPermissionLocalization(it.toLocalPermission())
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

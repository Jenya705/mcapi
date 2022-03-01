package com.github.jenya705.mcapi.server.command.linkmenu;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.application.BaseCommon;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.command.MenuCommand;
import com.github.jenya705.mcapi.server.command.NoConfig;
import com.github.jenya705.mcapi.server.module.config.message.MessageContainer;
import com.github.jenya705.mcapi.server.module.database.EventDatabaseStorage;
import com.github.jenya705.mcapi.server.module.localization.LocalizationModule;
import com.github.jenya705.mcapi.server.stringful.StringfulIterator;
import com.google.inject.Inject;

import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@NoConfig
public class LinkPermissionCommand extends MenuCommand implements BaseCommon {

    private final EventDatabaseStorage databaseStorage;
    private final LocalizationModule localizationModule;
    private final ServerApplication application;
    private final MessageContainer messageContainer;

    @Override
    public ServerApplication app() {
        return application;
    }

    @Inject
    public LinkPermissionCommand(ServerApplication application, EventDatabaseStorage databaseStorage,
                                 LocalizationModule localizationModule, MessageContainer messageContainer) {
        super(application);
        this.application = application;
        this.databaseStorage = databaseStorage;
        this.localizationModule = localizationModule;
        this.messageContainer = messageContainer;
    }

    @Override
    public void menuCommand(CommandSender sender, StringfulIterator args, String permission) throws Exception {
        if (!args.hasNext(1) || !(sender instanceof Player)) return;
        Player player = (Player) sender;
        int botId = Integer.parseInt(args.next());
        worker().invoke(() ->
                player.sendMessage(
                        messageContainer.render(
                                messageContainer.localizedPermissionList(
                                        databaseStorage
                                                .findPermissionsByIdAndTarget(botId, player.getUuid())
                                                .stream()
                                                .filter(it -> !it.isRegex())
                                                .map(it -> localizationModule
                                                        .getLinkPermissionLocalization(it.toLocalPermission())
                                                )
                                                .collect(Collectors.toList()),
                                        databaseStorage
                                                .findBotById(botId)
                                                .getName()
                                ),
                                sender
                        )
                )
        );
    }
}

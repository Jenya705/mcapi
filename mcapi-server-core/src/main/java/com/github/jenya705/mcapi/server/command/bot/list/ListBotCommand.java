package com.github.jenya705.mcapi.server.command.bot.list;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.command.AdditionalPermissions;
import com.github.jenya705.mcapi.server.command.NoConfig;
import com.github.jenya705.mcapi.server.command.RootCommand;
import com.github.jenya705.mcapi.server.command.advanced.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.server.module.config.message.MessageContainer;
import com.github.jenya705.mcapi.server.module.database.storage.EventDatabaseStorage;
import com.github.jenya705.mcapi.server.util.PlayerUtils;
import com.google.inject.Inject;

import java.util.Collections;

/**
 * @author Jenya705
 */
@NoConfig
@AdditionalPermissions("others")
public class ListBotCommand extends AdvancedCommandExecutor<ListBotArguments> {

    private final EventDatabaseStorage databaseStorage;

    @Inject
    public ListBotCommand(ServerApplication application, MessageContainer messageContainer, EventDatabaseStorage databaseStorage) {
        super(application, messageContainer, ListBotArguments.class);
        this.databaseStorage = databaseStorage;
        this
                .tab(() -> Collections.singletonList("<page>"))
                .tab((sender, permission) ->
                        hasPermission(sender, permission, "others") ? PlayerUtils.playerTabs(core()) : null
                );
    }

    @Override
    public void onCommand(CommandSender sender, ListBotArguments args, String permission) {
        if (args.getPlayer() != null && !hasPermission(sender, permission, "others")) {
            sendMessage(sender, messageContainer().notPermitted());
            return;
        }
        requirePlayer(
                sender,
                args.getPlayer(),
                (player) -> worker().invoke(() ->
                        sendMessage(
                                sender,
                                messageContainer().botList(
                                        databaseStorage
                                                .findBotsPageByOwner(
                                                        player.getUuid(),
                                                        args.getPage(),
                                                        RootCommand.maxListElements
                                                ),
                                        player.getName(),
                                        args.getPage() + 1
                                )
                        )
                )
        );
    }
}

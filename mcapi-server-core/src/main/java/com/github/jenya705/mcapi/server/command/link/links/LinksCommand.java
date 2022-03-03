package com.github.jenya705.mcapi.server.command.link.links;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.command.NoConfig;
import com.github.jenya705.mcapi.server.command.RootCommand;
import com.github.jenya705.mcapi.server.command.advanced.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.server.module.config.message.MessageContainer;
import com.github.jenya705.mcapi.server.module.database.storage.EventDatabaseStorage;
import com.github.jenya705.mcapi.server.util.Pair;
import com.github.jenya705.mcapi.server.util.PlayerUtils;
import com.google.inject.Inject;

import java.util.Collections;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@NoConfig
public class LinksCommand extends AdvancedCommandExecutor<LinksArguments> {

    private final EventDatabaseStorage databaseStorage;

    @Inject
    public LinksCommand(ServerApplication application, MessageContainer messageContainer, EventDatabaseStorage databaseStorage) {
        super(application, messageContainer, LinksArguments.class);
        this.databaseStorage = databaseStorage;
        this
                .tab(() -> Collections.singletonList("<page>"))
                .tab((sender, permission) ->
                        hasPermission(sender, permission, "others") ? PlayerUtils.playerTabs(core()) : null
                );
    }

    @Override
    public void onCommand(CommandSender sender, LinksArguments args, String permission) {
        requirePlayer(
                sender,
                args.getPlayer(),
                player -> worker().invoke(() ->
                        sendMessage(
                                sender,
                                messageContainer().linkList(
                                        databaseStorage
                                                .findLinksByTarget(player.getUuid())
                                                .stream()
                                                .map(it -> new Pair<>(
                                                        it, databaseStorage.findBotById(it.getBotId())
                                                ))
                                                .skip((long) args.getPage() * RootCommand.maxListElements)
                                                .limit(RootCommand.maxListElements)
                                                .collect(Collectors.toList()),
                                        player.getName(),
                                        args.getPage() + 1
                                )
                        )
                )
        );
    }
}

package com.github.jenya705.mcapi.server.command.bot.permission.list;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.command.AdditionalPermissions;
import com.github.jenya705.mcapi.server.command.CommandTabImpl;
import com.github.jenya705.mcapi.server.command.NoConfig;
import com.github.jenya705.mcapi.server.command.RootCommand;
import com.github.jenya705.mcapi.server.command.advanced.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.module.config.message.MessageContainer;
import com.github.jenya705.mcapi.server.module.database.DatabaseModule;
import com.google.inject.Inject;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@NoConfig
@AdditionalPermissions("others")
public class PermissionListBotCommand extends AdvancedCommandExecutor<PermissionListBotArguments> {

    private final DatabaseModule databaseModule;

    @Inject
    public PermissionListBotCommand(ServerApplication application, MessageContainer messageContainer, DatabaseModule databaseModule) {
        super(application, messageContainer, PermissionListBotArguments.class);
        this.databaseModule = databaseModule;
        this
                .databaseTab((sender, permission, databaseGetter) ->
                                sender instanceof Player ?
                                        databaseGetter
                                                .getBotsByOwner(((Player) sender).getUuid())
                                                .stream()
                                                .map(BotEntity::getName)
                                                .map(CommandTabImpl::of)
                                                .collect(Collectors.toList())
                                        : null,
                        true
                )
                .tab(() -> Collections.singletonList("<is_token>"))
                .tab(() -> Collections.singletonList("<page>"))
        ;
    }

    @Override
    public void onCommand(CommandSender sender, PermissionListBotArguments args, String permission) {
        worker().invoke(() -> {
            boolean isToken = args.isToken() || !(sender instanceof Player);
            BotEntity bot;
            UUID uuid = sender instanceof Player ? ((Player) sender).getUuid() : null;
            if (!isToken) {
                List<BotEntity> bots = databaseModule
                        .storage()
                        .findBotsByName(args.getName())
                        .stream()
                        .filter(it -> it.getOwner().equals(uuid))
                        .collect(Collectors.toList());
                if (bots.isEmpty()) {
                    sendMessage(sender, messageContainer().botNotFound());
                    return;
                }
                else if (bots.size() > 1) {
                    sendMessage(sender, messageContainer().provideToken());
                    return;
                }
                bot = bots.get(0);
            }
            else {
                bot = databaseModule
                        .storage()
                        .findBotByToken(args.getName());
                if (bot == null ||
                        (!bot.getOwner().equals(uuid) && !hasPermission(sender, permission, "others"))) {
                    sendMessage(sender, messageContainer().notPermitted());
                    return;
                }
            }
            sendMessage(
                    sender,
                    messageContainer().permissionList(
                            databaseModule
                                    .storage()
                                    .findPermissionsPageById(
                                            bot.getId(),
                                            args.getPage(),
                                            RootCommand.maxListElements
                                    ),
                            bot.getName(),
                            args.getPage() + 1
                    )
            );
        });
    }
}

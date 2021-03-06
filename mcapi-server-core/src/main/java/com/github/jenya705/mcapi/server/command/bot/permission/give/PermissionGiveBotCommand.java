package com.github.jenya705.mcapi.server.command.bot.permission.give;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.permission.PermissionFlag;
import com.github.jenya705.mcapi.player.OfflinePlayer;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.player.PlayerID;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.command.AdditionalPermissions;
import com.github.jenya705.mcapi.server.command.CommandTab;
import com.github.jenya705.mcapi.server.command.NoConfig;
import com.github.jenya705.mcapi.server.command.advanced.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.entity.BotPermissionEntity;
import com.github.jenya705.mcapi.server.module.config.message.MessageContainer;
import com.github.jenya705.mcapi.server.module.database.storage.EventDatabaseStorage;
import com.github.jenya705.mcapi.server.util.PlayerUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@NoConfig
@Singleton
@AdditionalPermissions("others")
public class PermissionGiveBotCommand extends AdvancedCommandExecutor<PermissionGiveBotArguments> {

    private final EventDatabaseStorage databaseStorage;

    @Inject
    public PermissionGiveBotCommand(ServerApplication application, MessageContainer messageContainer, EventDatabaseStorage databaseStorage) {
        super(application, messageContainer, PermissionGiveBotArguments.class);
        this.databaseStorage = databaseStorage;
        this
                .databaseTab((sender, permission, databaseGetter) ->
                                sender instanceof Player ?
                                        databaseGetter
                                                .getBotsByOwner(((Player) sender).getUuid())
                                                .stream()
                                                .map(BotEntity::getName)
                                                .map(CommandTab::of)
                                                .collect(Collectors.toList())
                                        : null,
                        true
                )
                .tab(() -> Collections.singletonList("<permission>"))
                .tab(() -> Arrays.asList("<is_toggled>", "true", "false"))
                .tab(() -> core()
                        .getPlayers()
                        .map(OfflinePlayer::getName)
                        .collect(Collectors.toList())
                        .block()
                )
                .tab(() -> Arrays.asList("<is_token>", "true", "false"))
                .tab(() -> Arrays.asList("<is_regex>", "true", "false"))
        ;
    }

    @Override
    public void onCommand(CommandSender sender, PermissionGiveBotArguments args, String permission) {
        PlayerID playerId = PlayerUtils.parsePlayerId(args.getTarget());
        UUID target = playerId.isUUID() ?
                playerId.getUuid() :
                playerId.isNickname() ?
                        core()
                                .getOfflinePlayer(playerId.toString())
                                .blockOptional()
                                .map(OfflinePlayer::getUuid)
                                .orElse(null) : null;
        worker().invoke(() -> {
            BotEntity bot;
            UUID uuid = sender instanceof Player ? ((Player) sender).getUuid() : null;
            if (args.isToken() || !(sender instanceof Player)) {
                bot = databaseStorage.findBotByToken(args.getBot());
                if (!bot.getOwner().equals(uuid) && !hasPermission(sender, permission, "others")) {
                    sendMessage(sender, messageContainer().notPermitted());
                    return;
                }
            }
            else {
                List<BotEntity> bots = databaseStorage.findBotsByName(args.getBot());
                if (bots.isEmpty()) {
                    sendMessage(sender, messageContainer().botNotFound());
                    return;
                }
                if (bots.size() > 1) {
                    sendMessage(sender, messageContainer().provideToken());
                    return;
                }
                bot = bots.get(0);
            }
                    if (!bot.getOwner().equals(uuid) && !hasPermission(sender, permission, "others")) {
                        sendMessage(sender, messageContainer().notPermitted());
                        return;
                    }
                    if (databaseStorage
                            .upsert(BotPermissionEntity
                                    .builder()
                                    .botId(bot.getId())
                                    .permission(args.isRegex() ?
                                            args.getPermission() :
                                            BotPermissionEntity.toRegex(args.getPermission())
                                    )
                                    .regex(args.isRegex())
                                    .permissionFlag(PermissionFlag.of(args.isToggled()))
                                    .target(target)
                                    .build()
                            )
                    ) {
                        sendMessage(sender, messageContainer().success());
                    }
                    else {
                        sendMessage(sender, messageContainer().failedInternal());
                    }
                }
        );
    }
}

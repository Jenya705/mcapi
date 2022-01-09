package com.github.jenya705.mcapi.server.command.bot.permission.give;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.OfflinePlayer;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.ServerApplication;
import com.github.jenya705.mcapi.server.command.AdditionalPermissions;
import com.github.jenya705.mcapi.server.command.CommandTab;
import com.github.jenya705.mcapi.server.command.advanced.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.entity.BotPermissionEntity;
import com.github.jenya705.mcapi.server.module.database.DatabaseModule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@AdditionalPermissions("others")
public class PermissionGiveBotCommand extends AdvancedCommandExecutor<PermissionGiveBotArguments> {

    private final DatabaseModule databaseModule = bean(DatabaseModule.class);

    private PermissionGiveBotConfig config;

    public PermissionGiveBotCommand(ServerApplication application) {
        super(application, PermissionGiveBotArguments.class);
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
                        .stream()
                        .map(OfflinePlayer::getName)
                        .collect(Collectors.toList())
                )
                .tab(() -> Arrays.asList("<is_token>", "true", "false"))
                .tab(() -> Arrays.asList("<is_regex>", "true", "false"))
        ;
    }

    @Override
    public void onCommand(CommandSender sender, PermissionGiveBotArguments args, String permission) {
        UUID target = args.getTarget() == null ? null : args.getTarget().getUuid();
        worker().invoke(() -> {
            BotEntity bot;
            UUID uuid = sender instanceof Player ? ((Player) sender).getUuid() : null;
            if (args.isToken() || !(sender instanceof Player)) {
                bot = databaseModule
                        .storage()
                        .findBotByToken(args.getBot());
                if (!bot.getOwner().equals(uuid) && !hasPermission(sender, permission, "others")) {
                    sendMessage(sender, config.getNotPermitted());
                    return;
                }
            }
            else {
                List<BotEntity> bots = databaseModule
                        .storage()
                        .findBotsByName(args.getBot());
                if (bots.isEmpty()) {
                    sendMessage(sender, config.getBotNotExist());
                    return;
                }
                if (bots.size() > 1) {
                    sendMessage(sender, config.getTooManyBots());
                    return;
                }
                bot = bots.get(0);
            }
            if (!bot.getOwner().equals(uuid) && !hasPermission(sender, permission, "others")) {
                sendMessage(sender, config.getNotPermitted());
                return;
            }
            databaseModule
                    .storage()
                    .upsert(BotPermissionEntity
                            .builder()
                            .botId(bot.getId())
                            .permission(args.isRegex() ?
                                    args.getPermission() :
                                    BotPermissionEntity.toRegex(args.getPermission())
                            )
                            .regex(args.isRegex())
                            .toggled(args.isToggled())
                            .target(target)
                            .build()
                    );
            sendMessage(sender, config.getSuccess());
        });
    }

    @Override
    public void setConfig(ConfigData config) {
        this.config = new PermissionGiveBotConfig(config);
        setConfig(this.config);
    }
}

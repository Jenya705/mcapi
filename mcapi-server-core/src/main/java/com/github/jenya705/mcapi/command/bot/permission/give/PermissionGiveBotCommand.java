package com.github.jenya705.mcapi.command.bot.permission.give;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.command.AdditionalPermissions;
import com.github.jenya705.mcapi.command.advanced.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.entity.BotEntity;
import com.github.jenya705.mcapi.entity.BotPermissionEntity;
import com.github.jenya705.mcapi.module.database.DatabaseModule;
import com.github.jenya705.mcapi.player.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author Jenya705
 */
@AdditionalPermissions("others")
public class PermissionGiveBotCommand extends AdvancedCommandExecutor<PermissionGiveBotArguments> {

    private final DatabaseModule databaseModule = bean(DatabaseModule.class);

    private PermissionGiveBotConfig config;

    public PermissionGiveBotCommand(ServerApplication application) {
        super(application, PermissionGiveBotArguments.class);
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
                                    BotPermissionEntity.toRegex(args.getPermission()) :
                                    args.getPermission()
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

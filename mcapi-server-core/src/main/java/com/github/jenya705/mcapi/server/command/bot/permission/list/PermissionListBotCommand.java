package com.github.jenya705.mcapi.server.command.bot.permission.list;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.ServerApplication;
import com.github.jenya705.mcapi.server.command.AdditionalPermissions;
import com.github.jenya705.mcapi.server.command.CommandTabImpl;
import com.github.jenya705.mcapi.server.command.CommandsUtils;
import com.github.jenya705.mcapi.server.command.advanced.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.module.database.DatabaseModule;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@AdditionalPermissions("others")
public class PermissionListBotCommand extends AdvancedCommandExecutor<PermissionListBotArguments> {

    private final DatabaseModule databaseModule;

    private PermissionListBotConfig config;

    public PermissionListBotCommand(ServerApplication application) {
        super(application, PermissionListBotArguments.class);
        databaseModule = bean(DatabaseModule.class);
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
                    sendMessage(sender, config.getBotNotExist());
                    return;
                }
                else if (bots.size() > 1) {
                    sendMessage(sender, config.getTooManyBots());
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
                    sendMessage(sender, config.getNotPermitted());
                    return;
                }
            }
            sendListMessage(
                    sender,
                    config.getListLayout(),
                    config.getListElement(),
                    config.getListDelimiter(),
                    databaseModule
                            .storage()
                            .findPermissionsPageById(
                                    bot.getId(),
                                    args.getPage(),
                                    config.getMaxElements()
                            ),
                    permissionEntity -> new String[]{
                            "%toggle%",
                            CommandsUtils.placeholderMessage(
                                    permissionEntity.isToggled() ?
                                            config.getPermissionEnabled() :
                                            config.getPermissionDisabled()
                            ),
                            "%permission_name%",
                            permissionEntity.toLocalPermission()
                    },
                    "%page%",
                    Integer.toString(args.getPage() + 1),
                    "%bot_name%",
                    bot.getName()
            );
        });
    }

    @Override
    public void setConfig(ConfigData config) {
        this.config = new PermissionListBotConfig(config);
        setConfig(this.config);
    }
}

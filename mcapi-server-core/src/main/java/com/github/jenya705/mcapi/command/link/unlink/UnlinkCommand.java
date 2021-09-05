package com.github.jenya705.mcapi.command.link.unlink;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.command.AdditionalPermissions;
import com.github.jenya705.mcapi.command.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.entity.BotLinkEntity;
import com.github.jenya705.mcapi.module.config.ConfigModule;
import com.github.jenya705.mcapi.module.config.GlobalConfig;
import com.github.jenya705.mcapi.module.database.DatabaseModule;

/**
 * @author Jenya705
 */
@AdditionalPermissions("others")
public class UnlinkCommand extends AdvancedCommandExecutor<UnlinkArguments> implements BaseCommon {

    private UnlinkConfig config;

    private final DatabaseModule databaseModule = bean(DatabaseModule.class);
    private final GlobalConfig globalConfig = bean(ConfigModule.class).global();

    public UnlinkCommand() {
        super(UnlinkArguments.class);
    }

    @Override
    public void onCommand(ApiCommandSender sender, UnlinkArguments args, String permission) {
        if (!globalConfig.isBotNameUnique()) {
            sendMessage(sender, config.getDisabledByAdmin());
            return;
        }
        if (args.getPlayer() != null && !hasPermission(sender, permission, "others")) {
            sendMessage(sender, config.getNotPermittedForOthers());
            return;
        }
        getPlayer(sender, args.getPlayer())
                .ifPresentOrElse(
                        (player) -> DatabaseModule.async.submit(() -> {
                            if (!databaseModule.storage().isExistBotWithName(args.getBotName())) {
                                sendMessage(sender, config.getNotLinked());
                                return;
                            }
                            BotLinkEntity linkEntity =
                                    databaseModule
                                            .storage()
                                            .findLink(
                                                    databaseModule
                                                            .storage()
                                                            .findBotsByName(args.getBotName())
                                                            .get(0)
                                                            .getId(),
                                                    player.getUuid()
                                            );
                            if (linkEntity == null) {
                                sendMessage(sender, config.getNotLinked());
                                return;
                            }
                            databaseModule
                                    .storage()
                                    .delete(linkEntity);
                            sendMessage(sender, config.getSuccess());
                        }),
                        () -> sendMessage(sender, config.getPlayerNotFound())
                );
    }

    @Override
    public void setConfig(ConfigData config) {
        this.config = new UnlinkConfig(config);
        setConfig(this.config);
    }
}

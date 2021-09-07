package com.github.jenya705.mcapi.command.bot.create;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.command.AdditionalPermissions;
import com.github.jenya705.mcapi.command.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.entity.BotEntity;
import com.github.jenya705.mcapi.module.config.ConfigModule;
import com.github.jenya705.mcapi.module.config.GlobalConfig;
import com.github.jenya705.mcapi.module.database.DatabaseModule;
import com.github.jenya705.mcapi.util.PlayerUtils;
import com.github.jenya705.mcapi.util.TokenUtils;

import java.util.Collections;

/**
 * @author Jenya705
 */
@AdditionalPermissions("others")
public class CreateBotCommand extends AdvancedCommandExecutor<CreateBotArguments> implements BaseCommon {

    private CreateBotConfig config;

    private final GlobalConfig globalConfig = bean(ConfigModule.class).global();
    private final DatabaseModule databaseModule = bean(DatabaseModule.class);

    public CreateBotCommand() {
        super(CreateBotArguments.class);
        this
                .tab(() -> Collections.singletonList("<bot_name>"))
                .tab((sender, permission) ->
                        hasPermission(sender, permission, "others") ? PlayerUtils.playerTabs(core()) : null
                );
    }

    @Override
    public void onCommand(ApiCommandSender sender, CreateBotArguments args, String permission) {
        if (args.getName().length() > 64) {
            sendMessage(sender, config.getBotNameTooLong());
            return;
        }
        if (args.getPlayer() != null && !hasPermission(sender, permission, "others")) {
            sendMessage(sender, config.getNotPermittedForOthers());
            return;
        }
        getPlayer(sender, args.getPlayer())
                .ifPresentOrElse(
                        (player) ->
                                DatabaseModule.async.submit(() -> {
                                    String generatedToken = TokenUtils.generateToken();
                                    if (!databaseModule
                                            .storage()
                                            .canCreateBot(args.getName(), globalConfig)
                                    ) {
                                        sendMessage(sender, config.getBotWithNameExist());
                                        return;
                                    }
                                    databaseModule
                                            .storage()
                                            .save(BotEntity.builder()
                                                    .name(args.getName())
                                                    .token(generatedToken)
                                                    .owner(player.getUuid())
                                                    .build()
                                            );
                                    sendMessage(sender,
                                            config.getSuccess(),
                                            "%token%", generatedToken
                                    );
                                }),
                        () -> sendMessage(sender, config.getPlayerNotFound())
                );
    }

    @Override
    public void setConfig(ConfigData config) {
        this.config = new CreateBotConfig(config);
        setConfig(this.config);
    }
}

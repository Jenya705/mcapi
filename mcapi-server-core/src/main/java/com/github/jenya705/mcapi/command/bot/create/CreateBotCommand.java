package com.github.jenya705.mcapi.command.bot.create;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.command.AdditionalPermissions;
import com.github.jenya705.mcapi.command.advanced.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.module.bot.BotManagement;
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
public class CreateBotCommand extends AdvancedCommandExecutor<CreateBotArguments> {

    private CreateBotConfig config;

    private final GlobalConfig globalConfig = bean(ConfigModule.class).global();
    private final DatabaseModule databaseModule = bean(DatabaseModule.class);
    private final BotManagement botManagement = bean(BotManagement.class);

    public CreateBotCommand(ServerApplication application) {
        super(application, CreateBotArguments.class);
        this
                .tab(() -> Collections.singletonList("<bot_name>"))
                .tab((sender, permission) ->
                        hasPermission(sender, permission, "others") ? PlayerUtils.playerTabs(core()) : null
                );
    }

    @Override
    public void onCommand(CommandSender sender, CreateBotArguments args, String permission) {
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
                                worker().invoke(() -> {
                                    String generatedToken = TokenUtils.generateToken();
                                    boolean canCreateBot = databaseModule
                                            .storage()
                                            .canCreateBot(args.getName(), globalConfig)
                                            &&
                                            botManagement
                                                    .addBot(
                                                            args.getName(),
                                                            player.getUuid(),
                                                            generatedToken
                                                    );
                                    if (!canCreateBot) {
                                        sendMessage(sender, config.getBotWithNameExist());
                                        return;
                                    }
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

package com.github.jenya705.mcapi.server.command.bot.create;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.command.AdditionalPermissions;
import com.github.jenya705.mcapi.server.command.NoConfig;
import com.github.jenya705.mcapi.server.command.advanced.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.server.module.bot.BotManagement;
import com.github.jenya705.mcapi.server.module.config.ConfigModule;
import com.github.jenya705.mcapi.server.module.config.GlobalConfig;
import com.github.jenya705.mcapi.server.module.config.message.MessageContainer;
import com.github.jenya705.mcapi.server.module.database.DatabaseModule;
import com.github.jenya705.mcapi.server.util.PlayerUtils;
import com.github.jenya705.mcapi.server.util.TokenUtils;
import com.google.inject.Inject;

import java.util.Collections;

/**
 * @author Jenya705
 */
@NoConfig
@AdditionalPermissions("others")
public class CreateBotCommand extends AdvancedCommandExecutor<CreateBotArguments> {

    private final GlobalConfig globalConfig;
    private final DatabaseModule databaseModule;
    private final BotManagement botManagement;

    @Inject
    public CreateBotCommand(ServerApplication application, ConfigModule configModule,
                            DatabaseModule databaseModule, BotManagement botManagement,
                            MessageContainer messageContainer) {
        super(application, messageContainer, CreateBotArguments.class);
        globalConfig = configModule.global();
        this.databaseModule = databaseModule;
        this.botManagement = botManagement;
        this
                .tab(() -> Collections.singletonList("<bot_name>"))
                .tab((sender, permission) ->
                        hasPermission(sender, permission, "others") ? PlayerUtils.playerTabs(core()) : null
                );
    }

    @Override
    public void onCommand(CommandSender sender, CreateBotArguments args, String permission) {
        if (args.getName().length() > 64) {
            sendMessage(sender, messageContainer().botNameTooLong());
            return;
        }
        if (args.getPlayer() != null && !hasPermission(sender, permission, "others")) {
            sendMessage(sender, messageContainer().notPermitted());
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
                                        sendMessage(sender, messageContainer().botNameUsed());
                                        return;
                                    }
                                    sendMessage(sender, messageContainer().botCreation(generatedToken));
                                }),
                        () -> sendMessage(sender, messageContainer().playerNotFound(args.getPlayer()))
                );
    }

}

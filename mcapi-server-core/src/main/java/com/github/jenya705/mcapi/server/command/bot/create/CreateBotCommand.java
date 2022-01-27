package com.github.jenya705.mcapi.server.command.bot.create;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.command.AdditionalPermissions;
import com.github.jenya705.mcapi.server.command.advanced.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.module.bot.BotManagement;
import com.github.jenya705.mcapi.server.module.config.ConfigModule;
import com.github.jenya705.mcapi.server.module.config.GlobalConfig;
import com.github.jenya705.mcapi.server.module.config.message.MessageContainer;
import com.github.jenya705.mcapi.server.module.database.DatabaseModule;
import com.github.jenya705.mcapi.server.module.message.Message;
import com.github.jenya705.mcapi.server.util.PlayerUtils;
import com.github.jenya705.mcapi.server.util.TokenUtils;
import com.google.inject.Inject;

import java.util.Collections;

/**
 * @author Jenya705
 */
@AdditionalPermissions("others")
public class CreateBotCommand extends AdvancedCommandExecutor<CreateBotArguments> {

    private CreateBotConfig config;

    private final GlobalConfig globalConfig;
    private final DatabaseModule databaseModule;
    private final BotManagement botManagement;
    private final MessageContainer messageContainer;

    @Inject
    public CreateBotCommand(ServerApplication application, ConfigModule configModule,
                            DatabaseModule databaseModule, BotManagement botManagement,
                            MessageContainer messageContainer) {
        super(application, CreateBotArguments.class);
        globalConfig = configModule.global();
        this.databaseModule = databaseModule;
        this.botManagement = botManagement;
        this.messageContainer = messageContainer;
        this
                .tab(() -> Collections.singletonList("<bot_name>"))
                .tab((sender, permission) ->
                        hasPermission(sender, permission, "others") ? PlayerUtils.playerTabs(core()) : null
                );
    }

    @Override
    public void onCommand(CommandSender sender, CreateBotArguments args, String permission) {
        if (args.getName().length() > 64) {
            sender.sendMessage(messageContainer.render(messageContainer.botNameTooLong(), sender));
            return;
        }
        if (args.getPlayer() != null && !hasPermission(sender, permission, "others")) {
            sender.sendMessage(messageContainer.render(messageContainer.notPermitted(), sender));
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
                                        sender.sendMessage(messageContainer.render(messageContainer.botNameUsed(), sender));
                                        return;
                                    }
                                    sender.sendMessage(messageContainer.render(messageContainer.botCreation(generatedToken), sender));
                                }),
                        () -> sender.sendMessage(messageContainer.render(messageContainer.playerNotFound(args.getPlayer()), sender))
                );
    }

    @Override
    public void setConfig(ConfigData config) {
        this.config = new CreateBotConfig(config);
        setConfig(this.config);
    }
}

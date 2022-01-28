package com.github.jenya705.mcapi.server.command.bot.delete;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.command.AdditionalPermissions;
import com.github.jenya705.mcapi.server.command.NoConfig;
import com.github.jenya705.mcapi.server.command.advanced.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.module.config.message.MessageContainer;
import com.github.jenya705.mcapi.server.module.database.DatabaseModule;
import com.google.inject.Inject;

import java.util.UUID;

/**
 * @author Jenya705
 */
@NoConfig
@AdditionalPermissions("others")
public class DeleteBotCommand extends AdvancedCommandExecutor<DeleteBotArguments> {

    private final DatabaseModule databaseModule;

    @Inject
    public DeleteBotCommand(ServerApplication application, MessageContainer messageContainer, DatabaseModule databaseModule) {
        super(application, messageContainer, DeleteBotArguments.class);
        this.databaseModule = databaseModule;
    }

    @Override
    public void onCommand(CommandSender sender, DeleteBotArguments args, String permission) {
        worker().invoke(() -> {
            BotEntity botEntity = databaseModule
                    .storage()
                    .findBotByToken(args.getToken());
            UUID senderUuid = sender instanceof Player ? ((Player) sender).getUuid() : null;
            if ((botEntity == null || !botEntity.getOwner().equals(senderUuid)) &&
                    hasPermission(sender, permission, "others")) {
                sendMessage(sender, messageContainer().notPermitted());
                return;
            }
            if (!args.getConfirm().equalsIgnoreCase("confirm")) {
                sendMessage(sender, messageContainer().botDeleteNotify());
                return;
            }
            databaseModule
                    .storage()
                    .delete(botEntity);
            sendMessage(sender, messageContainer().badSuccess());
        });
    }
}

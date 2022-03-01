package com.github.jenya705.mcapi.server.command.bot.delete;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.command.AdditionalPermissions;
import com.github.jenya705.mcapi.server.command.NoConfig;
import com.github.jenya705.mcapi.server.command.advanced.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.module.config.message.MessageContainer;
import com.github.jenya705.mcapi.server.module.database.EventDatabaseStorage;
import com.google.inject.Inject;

import java.util.UUID;

/**
 * @author Jenya705
 */
@NoConfig
@AdditionalPermissions("others")
public class DeleteBotCommand extends AdvancedCommandExecutor<DeleteBotArguments> {

    private final EventDatabaseStorage databaseStorage;

    @Inject
    public DeleteBotCommand(ServerApplication application, MessageContainer messageContainer, EventDatabaseStorage databaseStorage) {
        super(application, messageContainer, DeleteBotArguments.class);
        this.databaseStorage = databaseStorage;
    }

    @Override
    public void onCommand(CommandSender sender, DeleteBotArguments args, String permission) {
        worker().invoke(() -> {
            BotEntity botEntity = databaseStorage.findBotByToken(args.getToken());
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
            if (databaseStorage.delete(botEntity)) {
                sendMessage(sender, messageContainer().badSuccess());
            }
            else {
                sendMessage(sender, messageContainer().failedInternal());
            }
        });
    }
}

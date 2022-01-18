package com.github.jenya705.mcapi.server.command.bot.delete;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.command.AdditionalPermissions;
import com.github.jenya705.mcapi.server.command.advanced.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.module.database.DatabaseModule;
import com.google.inject.Inject;

import java.util.UUID;

/**
 * @author Jenya705
 */
@AdditionalPermissions("others")
public class DeleteBotCommand extends AdvancedCommandExecutor<DeleteBotArguments> {

    private DeleteBotConfig config;

    private final DatabaseModule databaseModule;

    @Inject
    public DeleteBotCommand(ServerApplication application, DatabaseModule databaseModule) {
        super(application, DeleteBotArguments.class);
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
                sendMessage(sender, config.getNotPermitted());
                return;
            }
            if (!args.getConfirm().equalsIgnoreCase("confirm")) {
                sendMessage(sender, config.getConfirm());
                return;
            }
            databaseModule
                    .storage()
                    .delete(botEntity);
            sendMessage(sender, config.getSuccess());
        });
    }

    @Override
    public void setConfig(ConfigData config) {
        this.config = new DeleteBotConfig(config);
        setConfig(this.config);
    }
}

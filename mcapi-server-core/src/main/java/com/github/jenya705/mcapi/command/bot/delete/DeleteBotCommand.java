package com.github.jenya705.mcapi.command.bot.delete;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.command.AdditionalPermissions;
import com.github.jenya705.mcapi.command.advanced.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.entity.BotEntity;
import com.github.jenya705.mcapi.module.database.DatabaseModule;

import java.util.UUID;

/**
 * @author Jenya705
 */
@AdditionalPermissions("others")
public class DeleteBotCommand extends AdvancedCommandExecutor<DeleteBotArguments> {

    private DeleteBotConfig config;

    private final DatabaseModule databaseModule = bean(DatabaseModule.class);

    public DeleteBotCommand(ServerApplication application) {
        super(application, DeleteBotArguments.class);
    }

    @Override
    public void onCommand(ApiCommandSender sender, DeleteBotArguments args, String permission) {
        DatabaseModule.async.submit(() -> {
            BotEntity botEntity = databaseModule
                    .storage()
                    .findBotByToken(args.getToken());
            UUID senderUuid = sender instanceof ApiPlayer ? ((ApiPlayer) sender).getUuid() : null;
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

package com.github.jenya705.mcapi.command.token;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.command.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.command.CommandsUtil;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.entity.BotEntity;
import com.github.jenya705.mcapi.module.database.DatabaseModule;
import com.github.jenya705.mcapi.util.TokenUtils;

import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
public class CreateBotCommand extends AdvancedCommandExecutor<CreateBotArguments> implements BaseCommon {

    private CreateBotConfig config;

    private final DatabaseModule databaseModule = bean(DatabaseModule.class);

    public CreateBotCommand() {
        super(CreateBotArguments.class);
        this
                .tab(() -> Collections.singletonList("<bot_name>"))
                .tab(() ->
                        core()
                                .getPlayers()
                                .stream()
                                .map(ApiPlayer::getName)
                                .collect(Collectors.toList())
                );
    }

    @Override
    public void onCommand(ApiCommandSender sender, CreateBotArguments args) {
        UUID toRegister = null;
        if (args.getPlayer() != null) {
            toRegister = core()
                    .getOptionalPlayer(args.getPlayer())
                    .map(ApiPlayer::getUuid)
                    .orElse(null);
        }
        else if (sender instanceof ApiPlayer){
            toRegister = ((ApiPlayer) sender).getUuid();
        }
        if (toRegister == null) {
            sender.sendMessage(CommandsUtil.placeholderMessage(config.getPlayerNotFound()));
        }
        else {
            String generatedToken = TokenUtils.generateToken();
            databaseModule
                    .storage()
                    .save(BotEntity.builder()
                            .name(args.getName())
                            .token(generatedToken)
                            .owner(toRegister)
                            .build()
                    );
            sender.sendMessage(CommandsUtil.placeholderMessage(
                    config.getSuccess(),
                    "%token%", generatedToken
            ));
        }

    }

    @Override
    public void setConfig(ConfigData config) {
        this.config = new CreateBotConfig(config);
        setConfig(this.config);
    }
}

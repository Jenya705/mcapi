package com.github.jenya705.mcapi.command.token;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.command.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.entity.BotEntity;
import com.github.jenya705.mcapi.module.database.DatabaseModule;
import com.github.jenya705.mcapi.util.TokenUtils;

import java.util.Collections;
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
        if (args.getName().length() > 64) {
            sendMessage(sender, config.getBotNameTooLong());
            return;
        }
        getPlayer(sender, args.getPlayer())
                .ifPresentOrElse(
                        (player) -> {
                            String generatedToken = TokenUtils.generateToken();
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
                        },
                        () -> sendMessage(sender, config.getPlayerNotFound())
                );
    }

    @Override
    public void setConfig(ConfigData config) {
        this.config = new CreateBotConfig(config);
        setConfig(this.config);
    }
}

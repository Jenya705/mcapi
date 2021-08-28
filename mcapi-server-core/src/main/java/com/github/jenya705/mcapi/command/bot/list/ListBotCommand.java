package com.github.jenya705.mcapi.command.bot.list;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.command.AdditionalPermissions;
import com.github.jenya705.mcapi.command.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.command.CommandsUtil;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.entity.BotEntity;
import com.github.jenya705.mcapi.module.database.DatabaseModule;
import com.github.jenya705.mcapi.util.PlayerUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@AdditionalPermissions("others")
public class ListBotCommand extends AdvancedCommandExecutor<ListBotArguments> implements BaseCommon {

    private ListBotConfig config;

    private final DatabaseModule databaseModule = bean(DatabaseModule.class);

    public ListBotCommand() {
        super(ListBotArguments.class);
        this
                .tab(() -> PlayerUtils.playerTabs(core()));
    }

    @Override
    public void onCommand(ApiCommandSender sender, ListBotArguments args, String permission) {
        if (args.getPlayer() != null && !hasPermission(sender, permission, "others")) {
            sendMessage(sender, config.getNotPermittedForOthers());
            return;
        }
        getPlayer(sender, args.getPlayer())
                .ifPresentOrElse(
                        (player) -> {
                            List<BotEntity> bots =
                                    databaseModule
                                            .storage()
                                            .findBotsByOwner(player.getUuid());
                            sendMessage(
                                    sender, config.getListLayout(),
                                    "%player_name%", player.getName(),
                                    "%list%", bots
                                            .stream()
                                            .map(bot -> CommandsUtil.placeholderMessage(
                                                    config.getListElement(),
                                                    "%name%", bot.getName(),
                                                    "%token%", bot.getToken()
                                            ))
                                            .collect(Collectors.joining(config.getListDelimiter()))
                            );
                        },
                        () -> sendMessage(sender, config.getPlayerNotFound())
                );
    }

    @Override
    public void setConfig(ConfigData config) {
        this.config = new ListBotConfig(config);
        setConfig(this.config);
    }
}

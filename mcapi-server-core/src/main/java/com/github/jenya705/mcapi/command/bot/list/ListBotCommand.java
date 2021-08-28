package com.github.jenya705.mcapi.command.bot.list;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.command.AdditionalPermissions;
import com.github.jenya705.mcapi.command.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.module.database.DatabaseModule;
import com.github.jenya705.mcapi.util.PlayerUtils;

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
                        (player) ->
                                sendListMessage(
                                        sender,
                                        config.getListLayout(),
                                        config.getListElement(),
                                        config.getListDelimiter(),
                                        databaseModule
                                                .storage()
                                                .findBotsByOwner(player.getUuid()),
                                        bot -> new String[]{
                                                "%name%", bot.getName(),
                                                "%token%", bot.getToken()
                                        }
                                ),
                        () -> sendMessage(sender, config.getPlayerNotFound())
                );
    }

    @Override
    public void setConfig(ConfigData config) {
        this.config = new ListBotConfig(config);
        setConfig(this.config);
    }
}

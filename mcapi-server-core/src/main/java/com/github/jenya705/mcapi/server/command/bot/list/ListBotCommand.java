package com.github.jenya705.mcapi.server.command.bot.list;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.server.ServerApplication;
import com.github.jenya705.mcapi.server.command.AdditionalPermissions;
import com.github.jenya705.mcapi.server.command.advanced.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.module.database.DatabaseModule;
import com.github.jenya705.mcapi.server.util.PlayerUtils;

import java.util.Collections;

/**
 * @author Jenya705
 */
@AdditionalPermissions("others")
public class ListBotCommand extends AdvancedCommandExecutor<ListBotArguments> {

    private ListBotConfig config;

    private final DatabaseModule databaseModule = bean(DatabaseModule.class);

    public ListBotCommand(ServerApplication application) {
        super(application, ListBotArguments.class);
        this
                .tab(() -> Collections.singletonList("<page>"))
                .tab((sender, permission) ->
                        hasPermission(sender, permission, "others") ? PlayerUtils.playerTabs(core()) : null
                );
    }

    @Override
    public void onCommand(CommandSender sender, ListBotArguments args, String permission) {
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
                                                .findBotsPageByOwner(
                                                        player.getUuid(),
                                                        args.getPage(),
                                                        config.getMaxElements()
                                                ),
                                        bot -> new String[]{
                                                "%name%", bot.getName(),
                                                "%token%", bot.getToken()
                                        },
                                        "%page%", Integer.toString(args.getPage() + 1),
                                        "%player_name%", player.getName()
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

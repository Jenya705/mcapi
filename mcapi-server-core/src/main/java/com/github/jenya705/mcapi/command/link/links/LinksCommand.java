package com.github.jenya705.mcapi.command.link.links;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.command.advanced.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.module.database.DatabaseModule;
import com.github.jenya705.mcapi.util.PlayerUtils;

import java.util.Collections;

/**
 * @author Jenya705
 */
public class LinksCommand extends AdvancedCommandExecutor<LinksArguments> {

    private LinksConfig config;

    private final DatabaseModule databaseModule = bean(DatabaseModule.class);

    public LinksCommand(ServerApplication application) {
        super(application, LinksArguments.class);
        this
                .tab(() -> Collections.singletonList("<page>"))
                .tab((sender, permission) ->
                        hasPermission(sender, permission, "others") ? PlayerUtils.playerTabs(core()) : null
                );
    }

    @Override
    public void onCommand(CommandSender sender, LinksArguments args, String permission) {
        getPlayer(sender, args.getPlayer())
                .ifPresentOrElse(
                        player -> worker().invoke(() ->
                                sendListMessage(
                                        sender,
                                        config.getListLayout(),
                                        config.getListElement(),
                                        config.getListDelimiter(),
                                        databaseModule
                                                .storage()
                                                .findLinksByTarget(player.getUuid()),
                                        it -> new String[]{
                                                "%name%",
                                                databaseModule
                                                        .storage()
                                                        .findBotById(it.getBotId())
                                                        .getName(),
                                                "%bot_id%", Integer.toString(it.getBotId())
                                        },
                                        config.getMaxElements(),
                                        args.getPage(),
                                        "%page%", Integer.toString(args.getPage() + 1),
                                        "%player%", player.getName()
                                )
                        ),
                        () -> sendMessage(sender, config.getPlayerNotFound())
                );
    }

    @Override
    public void setConfig(ConfigData config) {
        this.config = new LinksConfig(config);
        setConfig(this.config);
    }
}

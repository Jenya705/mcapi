package com.github.jenya705.mcapi.command.link.links;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.command.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.module.database.DatabaseModule;

/**
 * @author Jenya705
 */
public class LinksCommand extends AdvancedCommandExecutor<LinksArguments> implements BaseCommon {

    private LinksConfig config;

    private final DatabaseModule databaseModule = bean(DatabaseModule.class);

    public LinksCommand() {
        super(LinksArguments.class);
    }

    @Override
    public void onCommand(ApiCommandSender sender, LinksArguments args, String permission) {
        getPlayer(sender, args.getPlayer())
                .ifPresentOrElse(
                        player -> DatabaseModule.async.submit(() ->
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

package com.github.jenya705.mcapi.server.command.link.unlink;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.command.AdditionalPermissions;
import com.github.jenya705.mcapi.server.command.CommandTab;
import com.github.jenya705.mcapi.server.command.advanced.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.entity.BotLinkEntity;
import com.github.jenya705.mcapi.server.module.config.ConfigModule;
import com.github.jenya705.mcapi.server.module.config.GlobalConfig;
import com.github.jenya705.mcapi.server.module.database.DatabaseModule;
import com.github.jenya705.mcapi.server.module.link.LinkingModule;
import com.github.jenya705.mcapi.server.util.PlayerUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@AdditionalPermissions("others")
public class UnlinkCommand extends AdvancedCommandExecutor<UnlinkArguments> {

    private UnlinkConfig config;

    private final DatabaseModule databaseModule = bean(DatabaseModule.class);
    private final GlobalConfig globalConfig = bean(ConfigModule.class).global();
    private final LinkingModule linkingModule = bean(LinkingModule.class);

    public UnlinkCommand(ServerApplication application) {
        super(application, UnlinkArguments.class);
        this
                .databaseTab((sender, permission, databaseGetter) ->
                        Optional.of(sender)
                                .map(it -> it instanceof Player ? (Player) it : null)
                                .map(it -> databaseGetter
                                        .getLinks(it.getUuid())
                                        .stream()
                                        .map(bot ->
                                                Optional.ofNullable(
                                                        databaseGetter
                                                                .getBot(bot.getBotId())
                                                )
                                                        .map(safeBot -> CommandTab.of(safeBot.getName()))
                                                        .orElse(null)
                                        )
                                        .filter(Objects::nonNull)
                                        .collect(Collectors.toList())
                                )
                                .orElse(null), true
                )
                .tab((sender, permission) ->
                        hasPermission(sender, permission, "others") ? PlayerUtils.playerTabs(core()) : null
                );
    }

    @Override
    public void onCommand(CommandSender sender, UnlinkArguments args, String permission) {
        if (!globalConfig.isBotNameUnique()) {
            sendMessage(sender, config.getDisabledByAdmin());
            return;
        }
        if (args.getPlayer() != null && !hasPermission(sender, permission, "others")) {
            sendMessage(sender, config.getNotPermittedForOthers());
            return;
        }
        getPlayer(sender, args.getPlayer())
                .ifPresentOrElse(
                        (player) -> worker().invoke(() -> {
                            if (!databaseModule.storage().isExistBotWithName(args.getBotName())) {
                                sendMessage(sender, config.getNotLinked());
                                return;
                            }
                            BotEntity botEntity =
                                    databaseModule
                                            .storage()
                                            .findBotsByName(args.getBotName())
                                            .get(0);
                            BotLinkEntity linkEntity =
                                    databaseModule
                                            .storage()
                                            .findLink(
                                                    botEntity.getId(),
                                                    player.getUuid()
                                            );
                            if (linkEntity == null) {
                                sendMessage(sender, config.getNotLinked());
                                return;
                            }
                            linkingModule.unlink(botEntity, player);
                            sendMessage(sender, config.getSuccess());
                        }),
                        () -> sendMessage(sender, config.getPlayerNotFound())
                );
    }

    @Override
    public void setConfig(ConfigData config) {
        this.config = new UnlinkConfig(config);
        setConfig(this.config);
    }
}

package com.github.jenya705.mcapi.command.link.unlink;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.command.AdditionalPermissions;
import com.github.jenya705.mcapi.command.CommandTab;
import com.github.jenya705.mcapi.command.advanced.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.entity.BotEntity;
import com.github.jenya705.mcapi.entity.BotLinkEntity;
import com.github.jenya705.mcapi.module.config.ConfigModule;
import com.github.jenya705.mcapi.module.config.GlobalConfig;
import com.github.jenya705.mcapi.module.database.DatabaseModule;
import com.github.jenya705.mcapi.module.link.LinkingModule;
import com.github.jenya705.mcapi.util.PlayerUtils;

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
                                .map(it -> it instanceof ApiPlayer ? (ApiPlayer) it : null)
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
    public void onCommand(ApiCommandSender sender, UnlinkArguments args, String permission) {
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
                        (player) -> DatabaseModule.async.submit(() -> {
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

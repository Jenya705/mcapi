package com.github.jenya705.mcapi.server.module.selector;

import com.github.jenya705.mcapi.player.OfflinePlayer;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.util.Selector;
import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Mono;

/**
 * @author Jenya705
 */
public class ServerSelectorProvider extends AbstractApplicationModule implements SelectorProvider {

    private final BotSelectorCreator botSelectorCreator;
    private final PlayerSelectorCreator playerSelectorCreator;
    private final OfflineSelectorCreator offlinePlayerSelectorCreator;

    @Inject
    public ServerSelectorProvider(ServerApplication application) {
        super(application);
        playerSelectorCreator = new PlayerSelectorCreator(app());
        botSelectorCreator = new BotSelectorCreator(app());
        offlinePlayerSelectorCreator = new OfflineSelectorCreator(app());
    }

    @Override
    public Mono<Selector<Player>> players(@NotNull String selector, @Nullable AbstractBot bot) {
        return playerSelectorCreator.create(
                selector,
                new DefaultSelectorCreatorData(bot)
        );
    }

    @Override
    public Mono<Selector<AbstractBot>> bots(@NotNull String selector, @Nullable AbstractBot bot) {
        return botSelectorCreator.create(
                selector,
                new DefaultSelectorCreatorData(bot)
        );
    }

    @Override
    public Mono<Selector<OfflinePlayer>> offlinePlayers(@NotNull String selector, @Nullable AbstractBot bot) {
        return offlinePlayerSelectorCreator.create(
                selector,
                new DefaultSelectorCreatorData(bot)
        );
    }
}

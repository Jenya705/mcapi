package com.github.jenya705.mcapi.server.module.selector;

import com.github.jenya705.mcapi.player.OfflinePlayer;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.util.Selector;
import com.google.inject.ImplementedBy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Jenya705
 */
@ImplementedBy(ServerSelectorProvider.class)
public interface SelectorProvider {

    Selector<Player> players(@NotNull String selector, @Nullable AbstractBot bot);

    Selector<AbstractBot> bots(@NotNull String selector, @Nullable AbstractBot bot);

    Selector<OfflinePlayer> offlinePlayers(@NotNull String selector, @Nullable AbstractBot bot);

}

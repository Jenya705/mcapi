package com.github.jenya705.mcapi.module.selector;

import com.github.jenya705.mcapi.OfflinePlayer;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.util.Selector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Jenya705
 */
public interface SelectorProvider {

    Selector<Player> players(@NotNull String selector, @Nullable AbstractBot bot);

    Selector<AbstractBot> bots(@NotNull String selector, @Nullable AbstractBot bot);

    Selector<OfflinePlayer> offlinePlayers(@NotNull String selector, @Nullable AbstractBot bot);

}

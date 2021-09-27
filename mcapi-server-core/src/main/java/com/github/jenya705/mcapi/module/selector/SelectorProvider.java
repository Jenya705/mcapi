package com.github.jenya705.mcapi.module.selector;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.util.Selector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Jenya705
 */
public interface SelectorProvider {

    Selector<ApiPlayer> players(@NotNull String selector, @Nullable AbstractBot bot);

    Selector<AbstractBot> bots(@NotNull String selector, @Nullable AbstractBot bot);
}

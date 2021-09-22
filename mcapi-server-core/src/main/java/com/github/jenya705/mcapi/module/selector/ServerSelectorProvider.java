package com.github.jenya705.mcapi.module.selector;

import com.github.jenya705.mcapi.AbstractApplicationModule;
import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.OnInitializing;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.util.Selector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Jenya705
 */
public class ServerSelectorProvider extends AbstractApplicationModule implements SelectorProvider {

    private BotSelectorCreator botSelectorCreator;
    private PlayerSelectorCreator playerSelectorCreator;

    @OnInitializing
    public void init() {
        playerSelectorCreator = new PlayerSelectorCreator(app());
        botSelectorCreator = new BotSelectorCreator(app());
    }

    @Override
    public Selector<ApiPlayer> players(@NotNull String selector, @Nullable AbstractBot bot) {
        return playerSelectorCreator.create(
                selector,
                new PlayerSelectorCreator.Data(bot)
        );
    }

    @Override
    public Selector<AbstractBot> bots(@NotNull String selector, @Nullable AbstractBot bot) {
        return botSelectorCreator.create(
                selector,
                new BotSelectorCreator.Data(bot)
        );
    }
}

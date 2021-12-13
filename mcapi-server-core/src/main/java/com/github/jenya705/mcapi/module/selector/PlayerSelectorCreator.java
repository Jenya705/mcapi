package com.github.jenya705.mcapi.module.selector;

import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import com.github.jenya705.mcapi.player.Player;

import java.util.Collection;

/**
 * @author Jenya705
 */
public class PlayerSelectorCreator extends MapSelectorCreator<Player, DefaultSelectorCreatorData> implements BaseCommon {

    private final ServerApplication application;

    @Override
    public ServerApplication app() {
        return application;
    }

    @SuppressWarnings("unchecked")
    public PlayerSelectorCreator(ServerApplication application) {
        this.application = application;
        this
                .uuidDirect((data, id) ->
                        core()
                                .getOptionalPlayerId(id)
                                .orElseThrow(() -> PlayerNotFoundException.create(id))
                )
                .defaultSelector("a", data ->
                        (Collection<Player>) core().getPlayers()
                )
                .defaultSelector("r", data ->
                        SelectorCreatorUtils.randomSingleton(core().getPlayers())
                )
                .defaultSelector("l", data ->
                        SelectorCreatorUtils.botLinked(data.getBot(), linkEntity -> core().getPlayer(linkEntity.getTarget()))
                );
    }
}

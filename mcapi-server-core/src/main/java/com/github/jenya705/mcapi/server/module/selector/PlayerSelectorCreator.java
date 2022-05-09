package com.github.jenya705.mcapi.server.module.selector;

import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.application.BaseCommon;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

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
                                .getPlayer(id)
                                .flatMap(it -> Objects.isNull(it) ?
                                        Mono.error(PlayerNotFoundException.create(id)) :
                                        Mono.just(it)
                                )
                )
                .defaultSelector("a", data -> (Flux<Player>) core().getPlayers())
                .defaultSelector("r", data -> core().getPlayers()
                        .collectList()
                        .flatMapMany(it -> Flux.just(SelectorCreatorUtils.randomSingleton(it)))
                )
                .defaultSelector("l", data ->
                        SelectorCreatorUtils.botLinked(data.getBot(), it -> core().getPlayer(it.getTarget()))
                );
    }
}

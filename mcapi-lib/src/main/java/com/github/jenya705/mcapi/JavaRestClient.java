package com.github.jenya705.mcapi;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Jenya705
 */
public interface JavaRestClient extends RestClient {

    @Override
    Mono<? extends JavaPlayer> getPlayer(PlayerID playerID);

    @Override
    Flux<? extends JavaPlayer> getOnlinePlayers();
}

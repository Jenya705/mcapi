package com.github.jenya705.mcapi.reactor.rest;

import com.github.jenya705.mcapi.JavaPlayer;
import com.github.jenya705.mcapi.JavaRestClient;
import com.github.jenya705.mcapi.PlayerID;
import com.github.jenya705.mcapi.app.LibraryApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Jenya705
 */
public class JavaHttpRestClient extends HttpRestClient implements JavaRestClient {

    public JavaHttpRestClient(LibraryApplication application) {
        super(application);
    }

    @Override
    public Flux<? extends JavaPlayer> getOnlinePlayers() {
        return super.getOnlinePlayers().map(it -> (JavaPlayer) it);
    }

    @Override
    public Mono<? extends JavaPlayer> getPlayer(PlayerID id) {
        return super.getPlayer(id).map(it -> (JavaPlayer) it);
    }
}

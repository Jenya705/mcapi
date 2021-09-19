package com.github.jenya705.mcapi.module.web.reactor;

import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.util.ReactiveUtils;
import com.github.jenya705.mcapi.util.ReactorUtils;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServerRequest;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class ReactorRequest implements Request {

    private final ReactorServer reactorServer;
    private final HttpServerRequest request;

    @Override
    public <T> Mono<T> param(String key, Class<? extends T> clazz) {
        return ReactorUtils.mono(() ->
                reactorServer
                        .getMapper()
                        .fromRaw(
                                request.param(key),
                                clazz
                        )
        );
    }

    @Override
    public <T> Mono<T> body(Class<? extends T> clazz) {
        return ReactorUtils.mono(() ->
                reactorServer
                        .getMapper()
                        .fromJson(
                                request
                                        .receive()
                                        .aggregate()
                                        .asString()
                                        .doOnError(ReactiveUtils::runtimeException)
                                        .block(),
                                clazz
                        )
        );
    }

    @Override
    public <T> Mono<T> header(String key, Class<? extends T> clazz) {
        return ReactorUtils.mono(() ->
                reactorServer
                        .getMapper()
                        .fromRaw(
                                request.requestHeaders().get(key),
                                clazz
                        )
        );
    }
}

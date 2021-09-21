package com.github.jenya705.mcapi.module.web.reactor;

import com.github.jenya705.mcapi.module.web.Request;
import lombok.AllArgsConstructor;
import reactor.netty.http.server.HttpServerRequest;

import java.util.Optional;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class ReactorRequest implements Request {

    private final ReactorServer reactorServer;
    private final HttpServerRequest request;

    private final String body;

    @Override
    public Optional<String> body() {
        return Optional.ofNullable(body);
    }

    @Override
    public <T> Optional<T> param(String key, Class<? extends T> clazz) {
        return Optional.of(
                reactorServer
                        .getMapper()
                        .fromRaw(
                                request.param(key),
                                clazz
                        )
        );
    }

    @Override
    public <T> Optional<T> body(Class<? extends T> clazz) {
        return Optional.of(
                reactorServer
                        .getMapper()
                        .fromJson(
                                body,
                                clazz
                        )
        );
    }

    @Override
    public <T> Optional<T> header(String key, Class<? extends T> clazz) {
        return Optional.of(
                reactorServer
                        .getMapper()
                        .fromRaw(
                                request.requestHeaders().get(key),
                                clazz
                        )
        );
    }
}

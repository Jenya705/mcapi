package com.github.jenya705.mcapi.module.web.reactor;

import com.github.jenya705.mcapi.HttpMethod;
import com.github.jenya705.mcapi.module.web.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import reactor.netty.http.server.HttpServerRequest;

import java.util.Locale;
import java.util.Optional;

/**
 * @author Jenya705
 */
public class ReactorRequest implements Request {

    private final ReactorServer reactorServer;
    private final HttpServerRequest request;
    private final String body;

    @Getter
    private final HttpMethod method;

    public ReactorRequest(ReactorServer reactorServer, HttpServerRequest request, String body) {
        this.reactorServer = reactorServer;
        this.request = request;
        this.body = body;
        this.method = HttpMethod.valueOf(request.method().name().toUpperCase(Locale.ROOT));
    }

    @Override
    public String getUri() {
        return request.uri();
    }

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

package com.github.jenya705.mcapi.module.web.reactor;

import com.github.jenya705.mcapi.HttpMethod;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.module.web.RouteParameters;
import lombok.AllArgsConstructor;
import lombok.Getter;
import reactor.netty.http.server.HttpServerRequest;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * @author Jenya705
 */
public class ReactorRequest implements Request {

    private final ReactorServer reactorServer;
    private final HttpServerRequest request;
    private final String body;
    private final RouteParameters parameters;

    private Map<String, String> params;

    @Getter
    private final HttpMethod method;

    public ReactorRequest(ReactorServer reactorServer, HttpServerRequest request, String body, RouteParameters parameters) {
        this.reactorServer = reactorServer;
        this.request = request;
        this.body = body;
        this.parameters = parameters;
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
        if (params == null) {
            params = parameters.getParameters(request.uri());
        }
        return Optional.of(
                reactorServer
                        .getMapper()
                        .fromRaw(
                                params.get(key),
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

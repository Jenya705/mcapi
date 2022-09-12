package dev.mcapi.web.reactor;

import dev.mcapi.mapper.Mapper;
import dev.mcapi.web.Route;
import dev.mcapi.web.WebRequest;
import lombok.Getter;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServerRequest;

import java.util.HashMap;
import java.util.Map;

public class ReactorWebRequest implements WebRequest {

    private final HttpServerRequest request;
    private final Mapper mapper;

    @Getter
    private final Route route;

    @Getter
    private final Map<String, String> parameters = new HashMap<>();

    public ReactorWebRequest(HttpServerRequest request, Mapper mapper) {
        this.request = request;
        this.mapper = mapper;
        route = Route.of(
                Route.Type.valueOf(request.method().name()),
                request.uri()
        );
    }

    @Override
    public String getParameter(String name) {
        return parameters.get(name);
    }

    @Override
    public <T> T getParameter(String name, Class<T> clazz) {
        return mapper.fromRaw(getParameter(name), clazz);
    }

    @Override
    public Mono<String> getBody() {
        return request.receive()
                .aggregate()
                .asString();
    }

    @Override
    public <T> Mono<T> getBody(Class<T> clazz) {
        return getBody().map(json -> mapper.fromJson(json, clazz));
    }
}

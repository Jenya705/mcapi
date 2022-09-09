package dev.mcapi.web.reactor;

import dev.mcapi.mapper.ObjectMapper;
import dev.mcapi.web.Route;
import dev.mcapi.web.WebRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServerRequest;

import java.util.HashMap;
import java.util.Map;

public class ReactorWebRequest implements WebRequest {

    private final HttpServerRequest request;
    private final ObjectMapper objectMapper;

    @Getter
    private final Route route;

    @Getter
    private final Map<String, String> parameters = new HashMap<>();

    public ReactorWebRequest(HttpServerRequest request, ObjectMapper mapper) {
        this.request = request;
        this.objectMapper = mapper;
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
        return objectMapper.fromRaw(getParameter(name), clazz);
    }

    @Override
    public Mono<String> getBody() {
        return request.receive()
                .aggregate()
                .asString();
    }

    @Override
    public <T> Mono<T> getBody(Class<T> clazz) {
        return getBody().map(json -> objectMapper.fromJson(json, clazz));
    }
}

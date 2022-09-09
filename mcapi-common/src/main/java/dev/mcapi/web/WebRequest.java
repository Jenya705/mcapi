package dev.mcapi.web;

import reactor.core.publisher.Mono;

public interface WebRequest {

    default String getParameter(String name) {
        return getParameter(name, String.class);
    }

    <T> T getParameter(String name, Class<T> clazz);

    default Mono<String> getBody() {
        return getBody(String.class);
    }

    <T> Mono<T> getBody(Class<T> clazz);

    Route getRoute();

}

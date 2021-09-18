package com.github.jenya705.mcapi.module.web;

import reactor.core.publisher.Mono;

/**
 * @author Jenya705
 */
public interface Request {

    default Mono<String> param(String key) {
        return param(key, String.class);
    }

    default Mono<String> body() {
        return body(String.class);
    }

    default Mono<String> header(String key) {
        return header(key, String.class);
    }

    <T> Mono<T> param(String key, Class<? extends T> clazz);

    <T> Mono<T> body(Class<? extends T> clazz);

    <T> Mono<T> header(String key, Class<? extends T> clazz);

}

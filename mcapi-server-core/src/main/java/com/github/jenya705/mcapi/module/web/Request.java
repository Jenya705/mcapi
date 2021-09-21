package com.github.jenya705.mcapi.module.web;

import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author Jenya705
 */
public interface Request {

    default Optional<String> param(String key) {
        return param(key, String.class);
    }

    default Optional<String> body() {
        return body(String.class);
    }

    default Optional<String> header(String key) {
        return header(key, String.class);
    }

    <T> Optional<T> param(String key, Class<? extends T> clazz);

    <T> Optional<T> body(Class<? extends T> clazz);

    <T> Optional<T> header(String key, Class<? extends T> clazz);

}

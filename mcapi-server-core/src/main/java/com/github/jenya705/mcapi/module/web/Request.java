package com.github.jenya705.mcapi.module.web;

import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.error.AuthorizationBadTokenException;
import com.github.jenya705.mcapi.error.BodyIsEmptyException;
import com.github.jenya705.mcapi.util.ReactiveUtils;

import java.util.Optional;

/**
 * @author Jenya705
 */
public interface Request {

    default AbstractBot bot() {
        return header("Authorization", AbstractBot.class)
                .orElseThrow(AuthorizationBadTokenException::new);
    }

    default Optional<String> param(String key) {
        return param(key, String.class);
    }

    default Optional<String> body() {
        return body(String.class);
    }

    default Optional<String> header(String key) {
        return header(key, String.class);
    }

    default String paramOrException(String key) {
        return param(key).orElseThrow(ReactiveUtils::unknownException);
    }

    default <T> T paramOrException(String key, Class<? extends T> clazz) {
        return param(key, clazz).orElseThrow(ReactiveUtils::unknownException);
    }

    default String bodyOrException() {
        return body().orElseThrow(BodyIsEmptyException::new);
    }

    default <T> T bodyOrException(Class<? extends T> clazz) {
        return body(clazz).orElseThrow(BodyIsEmptyException::new);
    }

    <T> Optional<T> param(String key, Class<? extends T> clazz);

    <T> Optional<T> body(Class<? extends T> clazz);

    <T> Optional<T> header(String key, Class<? extends T> clazz);
}

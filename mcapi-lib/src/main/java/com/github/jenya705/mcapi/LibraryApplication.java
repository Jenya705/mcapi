package com.github.jenya705.mcapi;

import reactor.core.publisher.Mono;

/**
 * @author Jenya705
 */
public interface LibraryApplication {

    <T> Mono<T> deserialize(String text, Class<? extends T> clazz);

    default String serialize(Object object) {
        return serialize(object, object.getClass());
    }

    String serialize(Object object, Class<?> asClass);

    RestClient rest();
}

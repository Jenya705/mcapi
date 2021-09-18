package com.github.jenya705.mcapi.util;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Jenya705
 */
public interface MultivaluedMap<T, V> extends Map<T, Collection<V>> {

    static <T, V> MultivaluedMap<T, V> of(Map<T, Collection<V>> map) {
        return new MultivaluedMapImpl<>(map);
    }

    void add(T key, V value);

    default void forEach(T key, Consumer<V> consumer) {
        get(key).forEach(consumer);
    }

}

package com.github.jenya705.mcapi.util;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Jenya705
 */
public interface MultivaluedMap<T, V> extends Map<T, List<V>> {

    static <T, V> MultivaluedMap<T, V> of(Map<T, List<V>> map) {
        return new MultivaluedMapImpl<>(map);
    }

    void add(T key, V value);

    default void forEach(T key, Consumer<V> consumer) {
        get(key).forEach(consumer);
    }

}

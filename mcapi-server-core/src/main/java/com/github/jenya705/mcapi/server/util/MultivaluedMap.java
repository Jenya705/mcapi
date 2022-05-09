package com.github.jenya705.mcapi.server.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Jenya705
 */
public interface MultivaluedMap<T, V> extends Map<T, List<V>> {

    static <T, V> MultivaluedMap<T, V> of(Map<T, List<V>> map, Supplier<List<V>> listSupplier) {
        return new MultivaluedMapImpl<>(map, listSupplier);
    }

    static <T, V> MultivaluedMap<T, V> ofConcurrent(Map<T, List<V>> map) {
        return new MultivaluedMapImpl<>(map, CopyOnWriteArrayList::new);
    }

    static <T, V> MultivaluedMap<T, V> ofNotThreadSafe(Map<T, List<V>> map) {
        return new MultivaluedMapImpl<>(map, ArrayList::new);
    }

    static <T, V> MultivaluedMap<T, V> create() {
        return new MultivaluedMapImpl<>();
    }

    static <T, V> MultivaluedMap<T, V> concurrent() {
        return ofConcurrent(new ConcurrentHashMap<>());
    }

    void add(T key, V value);

    boolean removeElement(T key, V value);

    default void forEach(T key, Consumer<V> consumer) {
        get(key).forEach(consumer);
    }
}

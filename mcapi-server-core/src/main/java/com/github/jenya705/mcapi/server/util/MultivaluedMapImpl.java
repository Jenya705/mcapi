package com.github.jenya705.mcapi.server.util;

import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class MultivaluedMapImpl<T, V> implements MultivaluedMap<T, V> {

    @Delegate
    private final Map<T, List<V>> map;
    private final Supplier<List<V>> listSupplier;

    public MultivaluedMapImpl() {
        this(new HashMap<>(), ArrayList::new);
    }

    @Override
    public void add(T key, V value) {
        map.computeIfAbsent(key, it -> listSupplier.get()).add(value);
    }

    @Override
    public boolean removeElement(T key, V value) {
        return map.getOrDefault(key, Collections.emptyList()).remove(value);
    }
}

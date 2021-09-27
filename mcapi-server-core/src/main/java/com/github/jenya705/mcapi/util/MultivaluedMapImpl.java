package com.github.jenya705.mcapi.util;

import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class MultivaluedMapImpl<T, V> implements MultivaluedMap<T, V> {

    @Delegate
    private final Map<T, List<V>> map;

    @Override
    public void add(T key, V value) {
        if (map.containsKey(value)) map.get(key).add(value);
        else map.put(key, new ArrayList<>());
    }
}

package com.github.jenya705.mcapi.util;

import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class MultivaluedMapImpl<T, V> implements MultivaluedMap<T, V> {

    @Delegate
    private final Map<T, List<V>> map;

    public MultivaluedMapImpl() {
        this(new HashMap<>());
    }

    @Override
    public void add(T key, V value) {
        List<V> list;
        if (map.containsKey(key)) list = map.get(key);
        else map.put(key, list = new ArrayList<>());
        list.add(value);
    }
}

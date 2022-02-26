package com.github.jenya705.mcapi.server.util;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jenya705
 */
@UtilityClass
public class MapUtils {

    public <T, E> Map<T, E> hashMap(Collection<Pair<T, E>> list) {
        Map<T, E> map = new HashMap<>();
        addToMap(map, list);
        return map;
    }

    public <T, E> Map<T, E> concurrentHashMap(Collection<Pair<T, E>> list) {
        Map<T, E> map = new ConcurrentHashMap<>();
        addToMap(map, list);
        return map;
    }

    public <T, E> void addToMap(Map<T, E> map, Collection<Pair<T, E>> list) {
        list.forEach(it -> map.put(it.getLeft(), it.getRight()));
    }

}

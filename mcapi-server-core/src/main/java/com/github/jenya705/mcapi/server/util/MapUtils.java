package com.github.jenya705.mcapi.server.util;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jenya705
 */
@UtilityClass
public class MapUtils {

    public <T, E> Map<T, E> hashMap(List<Pair<T, E>> list) {
        Map<T, E> map = new HashMap<>();
        list.forEach(it -> map.put(it.getLeft(), it.getRight()));
        return map;
    }

}

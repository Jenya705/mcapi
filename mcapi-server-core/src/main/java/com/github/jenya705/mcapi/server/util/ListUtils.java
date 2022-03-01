package com.github.jenya705.mcapi.server.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Jenya705
 */
@UtilityClass
public class ListUtils {

    @SafeVarargs
    public <T> List<T> join(Collection<T>... lists) {
        List<T> newList = new ArrayList<>();
        for (Collection<T> list : lists) {
            newList.addAll(list);
        }
        return newList;
    }

    @SafeVarargs
    public <T> List<T> join(T[]... arrays) {
        List<T> newList = new ArrayList<>();
        for (T[] array : arrays) {
            if (array == null) continue;
            newList.addAll(Arrays.asList(array));
        }
        return newList;
    }

    public <T> List<T> join(Collection<? extends Collection<T>> lists) {
        List<T> newList = new ArrayList<>();
        for (Collection<T> list : lists) {
            newList.addAll(list);
        }
        return newList;
    }
}
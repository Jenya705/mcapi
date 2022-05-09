package com.github.jenya705.mcapi.server.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@UtilityClass
public class ListUtils {

    @SafeVarargs
    public <T> List<T> joinCopy(Collection<T>... lists) {
        List<T> newList = new ArrayList<>();
        for (Collection<T> list : lists) {
            newList.addAll(list);
        }
        return newList;
    }

    @SafeVarargs
    public <T> List<T> joinCopy(T[]... arrays) {
        List<T> newList = new ArrayList<>();
        for (T[] array : arrays) {
            if (array == null) continue;
            newList.addAll(Arrays.asList(array));
        }
        return newList;
    }

    public <T> List<T> joinCopy(Collection<? extends Collection<T>> lists) {
        List<T> newList = new ArrayList<>();
        for (Collection<T> list : lists) {
            newList.addAll(list);
        }
        return newList;
    }

    @SafeVarargs
    public <T> List<T> join(T[]... arrays) {
        return join(Arrays
                .stream(arrays)
                .map(Arrays::asList)
                .collect(Collectors.toList())
        );
    }

    @SafeVarargs
    public <T> List<T> join(List<T>... lists) {
        return join(Arrays.asList(lists));
    }

    public <T> List<T> joinCollection(List<Collection<T>> lists) {
        return join(lists
                .stream()
                .map(it -> it instanceof List ? (List<T>) it : new ArrayList<>(it))
                .collect(Collectors.toList())
        );
    }

    public <T> List<T> join(List<List<T>> lists) {
        return new JoinedList<>(lists);
    }

    @SafeVarargs
    public <T> T[] joinArray(IntFunction<T[]> creator, T[]... arrays) {
        int size = 0;
        for (T[] array: arrays) {
            size += array.length;
        }
        T[] result = creator.apply(size);
        int i = 0;
        for (T[] array: arrays) {
            for (T element: array) {
                result[i++] = element;
            }
        }
        return result;
    }

}
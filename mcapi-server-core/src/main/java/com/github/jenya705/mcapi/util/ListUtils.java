package com.github.jenya705.mcapi.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jenya705
 */
@UtilityClass
public class ListUtils {

    @SafeVarargs
    public <T> List<T> join(List<T>... lists) {
        List<T> newList = new ArrayList<>();
        for (List<T> list : lists) {
            newList.addAll(list);
        }
        return newList;
    }

    @SafeVarargs
    public <T> List<T> join(T[]... arrays) {
        List<T> newList = new ArrayList<>();
        for (T[] array : arrays) {
            newList.addAll(Arrays.asList(array));
        }
        return newList;
    }

    public <T> List<T> join(List<List<T>> lists) {
        List<T> newList = new ArrayList<>();
        for (List<T> list: lists) {
            newList.addAll(list);
        }
        return newList;
    }

}

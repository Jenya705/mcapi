package com.github.jenya705.mcapi.server.util;

import java.util.AbstractList;
import java.util.List;

/**
 *
 * Immutable list which is joining lists
 *
 * @author Jenya705
 */
public class JoinedList<T> extends AbstractList<T> {

    private final List<List<T>> lists;

    public JoinedList(List<List<T>> lists) {
        this.lists = lists;
    }

    @Override
    public T get(int index) {
        int cur = index;
        for (List<T> list: lists) {
            if (list.size() <= cur) {
                cur -= list.size();
            }
            else {
                return list.get(cur);
            }
        }
        throw outOfBounds(index);
    }

    @Override
    public int size() {
        return lists.stream().mapToInt(List::size).sum();
    }

    private RuntimeException outOfBounds(int index) {
        return new IndexOutOfBoundsException("Out of bounds. Index: " + index + " Size: " + size());
    }

}

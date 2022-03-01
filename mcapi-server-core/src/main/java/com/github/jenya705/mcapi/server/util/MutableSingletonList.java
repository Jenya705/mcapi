package com.github.jenya705.mcapi.server.util;

import lombok.AllArgsConstructor;

import java.util.AbstractList;
import java.util.Collection;
import java.util.Objects;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class MutableSingletonList<T> extends AbstractList<T> {

    private T obj;

    @Override
    public T get(int index) {
        validateOutOfBounds(index);
        return obj;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public T set(int index, T element) {
        validateOutOfBounds(index);
        T toReturn = obj;
        obj = element;
        return toReturn;
    }

    @Override
    public int indexOf(Object o) {
        return contains(o) ? 0 : -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        return indexOf(o);
    }

    @Override
    public boolean contains(Object o) {
        return Objects.equals(o, obj);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c.isEmpty()) return true;
        if (c.size() > 1) return false;
        for (Object collectionObj: c) {
            return contains(collectionObj);
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    private void validateOutOfBounds(int index) {
        if (index != 0) {
            throw new IndexOutOfBoundsException("Index: " + index + " Size: 1");
        }
    }

}

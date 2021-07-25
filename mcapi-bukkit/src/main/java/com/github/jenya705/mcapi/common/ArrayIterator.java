package com.github.jenya705.mcapi.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Iterator;

/**
 * @since 1.0
 * @author Jenya705
 */
@Getter
@AllArgsConstructor
public class ArrayIterator<T> implements Iterator<T> {

    private final T[] args;
    private int current;

    public ArrayIterator(T[] args) {
        this.args = args;
    }

    @Override
    public boolean hasNext() {
        return args.length > current;
    }

    @Override
    public T next() {
        return args[current++];
    }
}

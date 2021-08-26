package com.github.jenya705.mcapi.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author Jenya705
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pair<T, V> implements Map.Entry<T, V> {

    private T left;
    private V right;

    @Override
    public T getKey() {
        return left;
    }

    @Override
    public V getValue() {
        return right;
    }

    @Override
    public V setValue(V value) {
        right = value;
        return right;
    }
}

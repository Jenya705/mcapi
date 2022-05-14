package com.github.jenya705.mcapi.server.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * @author Jenya705
 */
@Data
@AllArgsConstructor
public class Pair<T, V> {

    public static <T, V> Pair<T, V> fromEntry(Map.Entry<? extends T, ? extends V> entry) {
        return new Pair<>(entry.getKey(), entry.getValue());
    }

    private final T left;
    private final V right;

    public Pair() {
        this(null, null);
    }

    public Pair<T, V> setLeft(T left) {
        return new Pair<>(left, right);
    }

    public Pair<T, V> setRight(V right) {
        return new Pair<>(left, right);
    }

    public MutablePair<T, V> mutable() {
        return new MutablePair<>(left, right);
    }

}

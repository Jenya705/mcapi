package com.github.jenya705.mcapi.server.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MutablePair<T, V> {

    private T left;
    private V right;

    public Pair<T, V> immutable() {
        return new Pair<>(left, right);
    }

}

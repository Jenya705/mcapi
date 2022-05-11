package com.github.jenya705.mcapi.server.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.function.Supplier;

@Data
@AllArgsConstructor
public class ValueContainer<T> implements Supplier<T> {

    private final T value;

    public MutableValueContainer<T> mutable() {
        return new MutableValueContainer<>(value);
    }

    @Override
    public T get() {
        return value;
    }
}

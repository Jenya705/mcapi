package com.github.jenya705.mcapi.server.util;

import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 *
 * Compute value when it gets first then contains it
 *
 * @author Jenya705
 */
@RequiredArgsConstructor
public class LazyInitializer<T> implements Supplier<T> {

    private final Supplier<T> get;
    private final Consumer<T> post;

    private volatile boolean initialized;
    private volatile T value;

    public LazyInitializer(Supplier<T> get) {
        this(get, null);
    }

    @Override
    public T get() {
        if (initialized) return value;
        synchronized (this) {
            if (initialized) return value;
            value = get.get();
            initialized = true;
            post.accept(value);
            return value;
        }
    }
}

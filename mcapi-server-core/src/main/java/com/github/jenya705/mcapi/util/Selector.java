package com.github.jenya705.mcapi.util;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author Jenya705
 */
public interface Selector<T> {

    Stream<T> stream();

    void forEach(Consumer<T> consumer);

    String getPermissionName();

    UUID getTarget();

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

    default boolean isSingleton() {
        return size() == 1;
    }

    default boolean isMulti() {
        return size() > 1;
    }
}

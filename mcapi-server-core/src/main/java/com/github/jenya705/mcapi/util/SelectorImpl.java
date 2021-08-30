package com.github.jenya705.mcapi.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author Jenya705
 */
@Getter
@AllArgsConstructor
public class SelectorImpl<T> implements Selector<T> {

    private final Collection<T> collection;
    private final String permissionName;
    private final UUID target;

    public static <E> SelectorImpl<E> singleton(E selector, String permissionName, Function<E, UUID> getTarget) {
        return new SelectorImpl<E>(
                Collections.singletonList(selector),
                permissionName,
                getTarget.apply(selector)
        );
    }

    public static <E> SelectorImpl<E> empty() {
        return new SelectorImpl<>(
                Collections.emptyList(),
                "",
                null
        );
    }

    @Override
    public Stream<T> stream() {
        return collection.stream();
    }

    @Override
    public void forEach(Consumer<T> consumer) {
        collection.forEach(consumer);
    }

    @Override
    public int size() {
        return collection.size();
    }
}

package com.github.jenya705.mcapi.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author Jenya705
 */
@Getter
@AllArgsConstructor
public class SelectorContainer<T> implements Selector<T> {

    private static final SelectorContainer<?> empty = new SelectorContainer<>(Collections.emptyList(), "", null);

    private final Collection<T> selectors;
    private final String permissionName;
    private final UUID target;

    public SelectorContainer(T selector, String permissionName, UUID target) {
        this(Collections.singletonList(selector), permissionName, target);
    }

    @SuppressWarnings("unchecked")
    public static <T> SelectorContainer<T> empty() {
        return (SelectorContainer<T>) empty;
    }

    @Override
    public Stream<T> stream() {
        return selectors.stream();
    }

    @Override
    public int size() {
        return selectors.size();
    }

    @Override
    public void forEach(Consumer<T> consumer) {
        selectors.forEach(consumer);
    }
}

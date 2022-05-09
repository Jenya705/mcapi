package com.github.jenya705.mcapi.server.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    private final Collection<T> all;
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
    public Collection<T> all() {
        return all;
    }

}

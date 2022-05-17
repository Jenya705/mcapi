package com.github.jenya705.mcapi.server.util;

import com.github.jenya705.mcapi.error.SelectorEmptyException;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * @author Jenya705
 */
public interface Selector<T> {

    Collection<T> all();

    String getPermissionName();

    UUID getTarget();

    default Mono<Selector<T>> errorIfEmpty(Supplier<? extends Throwable> throwable) {
        return all().isEmpty() ? Mono.error(throwable) : Mono.just(this);
    }

    default Mono<Selector<T>> errorIfEmpty() {
        return all().isEmpty() ? Mono.error(SelectorEmptyException::create) : Mono.just(this);
    }

}

package com.github.jenya705.mcapi.server.util;

import lombok.experimental.UtilityClass;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * @author Jenya705
 */
@UtilityClass
public class ReactorUtils {

    public <T> Mono<T> mono(Callable<T> from) {
        try {
            return Mono.just(from.call());
        } catch (Throwable e) {
            return Mono.error(e);
        }
    }

    public <T> Flux<T> flux(Callable<T[]> from) {
        try {
            return Flux.just(from.call());
        } catch (Throwable e) {
            return Flux.error(e);
        }
    }

    public <T> Mono<T> ifNullError(T obj, Supplier<Throwable> supplier) {
        return ifTrueError(obj, Objects.isNull(obj), supplier);
    }

    public <T> Mono<T> ifFalseError(T obj, boolean condition, Supplier<Throwable> supplier) {
        return ifTrueError(obj, !condition, supplier);
    }

    public <T> Mono<T> ifTrueError(T obj, boolean condition, Supplier<Throwable> supplier) {
        if (condition) return Mono.error(supplier.get());
        return Mono.just(obj);
    }
}

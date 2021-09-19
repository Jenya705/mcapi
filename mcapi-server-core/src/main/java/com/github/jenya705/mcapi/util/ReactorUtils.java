package com.github.jenya705.mcapi.util;

import lombok.experimental.UtilityClass;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

/**
 * @author Jenya705
 */
@UtilityClass
public class ReactorUtils {

    public <T> Mono<T> mono(Supplier<T> from) {
        try {
            return Mono.just(from.get());
        } catch (Throwable e) {
            return Mono.error(e);
        }
    }

}

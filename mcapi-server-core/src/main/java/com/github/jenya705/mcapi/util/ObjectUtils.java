package com.github.jenya705.mcapi.util;

import lombok.experimental.UtilityClass;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Jenya705
 */
@UtilityClass
public class ObjectUtils {

    public <T, E> T ifNotNullProcessOrElseGet(E object, Function<E, T> notNullFunction, Supplier<T> supplier) {
        return object == null ? supplier.get() : notNullFunction.apply(object);
    }

    public <T, E> T ifNotNullProcessOrElse(E object, Function<E, T> notNullFunction, T orElse) {
        return object == null ? orElse : notNullFunction.apply(object);
    }
}

package com.github.jenya705.mcapi.util;

import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Jenya705
 */
@UtilityClass
public class ReactiveUtils {

    public void ifEqualsThrow(Object obj, Object value, Supplier<RuntimeException> exceptionSupplier) {
        if (Objects.equals(obj, value)) throw exceptionSupplier.get();
    }

    public void ifNotEqualsThrow(Object obj, Object value, Supplier<RuntimeException> exceptionSupplier) {
        if (!Objects.equals(obj, value)) throw exceptionSupplier.get();
    }

    public void ifTrueThrow(boolean obj, Supplier<RuntimeException> exceptionSupplier) {
        if (obj) throw exceptionSupplier.get();
    }

    public void runtimeException(Throwable e) {
        throw new RuntimeException(e);
    }

    public RuntimeException unknownException() { return new RuntimeException("Unknown exception"); }

}

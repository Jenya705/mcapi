package com.github.jenya705.mcapi.server.util;

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

    public RuntimeException runtimeException(Throwable e) {
        if (e instanceof RuntimeException) return (RuntimeException) e;
        return new RuntimeException(e);
    }

    public RuntimeException unknownException() {
        return new RuntimeException("Unknown exception");
    }

    public void throwRuntimeException(Throwable e) {
        throw runtimeException(e);
    }
}

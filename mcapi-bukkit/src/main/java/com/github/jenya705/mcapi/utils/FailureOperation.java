package com.github.jenya705.mcapi.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Supplier;

/**
 * @author Jenya705
 */
@Getter
@AllArgsConstructor
public class FailureOperation {

    private final boolean success;

    public static FailureOperation success() {
        return new FailureOperation(true);
    }

    public static FailureOperation failed() {
        return new FailureOperation(false);
    }

    public FailureOperation ifSuccess(Supplier<Boolean> supplier) {
        if (success) {
            return new FailureOperation(supplier.get());
        }
        return this;
    }

    public FailureOperation ifFailed(Supplier<Boolean> supplier) {
        if (!success) {
            return new FailureOperation(supplier.get());
        }
        return this;
    }

    public FailureOperation ifSuccess(Runnable runnable) {
        if (success) runnable.run();
        return this;
    }

    public FailureOperation ifFailed(Runnable runnable) {
        if (success) runnable.run();
        return this;
    }

    public FailureOperation flatFailed(Supplier<FailureOperation> supplier) {
        if (!success) {
            return supplier.get();
        }
        return this;
    }

    public FailureOperation flatSuccess(Supplier<FailureOperation> supplier) {
        if (success) {
            return supplier.get();
        }
        return this;
    }

}

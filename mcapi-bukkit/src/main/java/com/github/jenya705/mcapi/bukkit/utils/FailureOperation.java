package com.github.jenya705.mcapi.bukkit.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Supplier;

/**
 * @author Jenya705
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FailureOperation {

    private static final FailureOperation FAILED = new FailureOperation(false);
    private static final FailureOperation SUCCESS = new FailureOperation(true);

    private final boolean success;

    public static FailureOperation of(boolean success) {
        return success ? success() : failed();
    }

    public static FailureOperation success() {
        return SUCCESS;
    }

    public static FailureOperation failed() {
        return FAILED;
    }

    public FailureOperation ifSuccess(Supplier<Boolean> supplier) {
        if (success) {
            return FailureOperation.of(supplier.get());
        }
        return this;
    }

    public FailureOperation ifFailed(Supplier<Boolean> supplier) {
        if (!success) {
            return FailureOperation.of(supplier.get());
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

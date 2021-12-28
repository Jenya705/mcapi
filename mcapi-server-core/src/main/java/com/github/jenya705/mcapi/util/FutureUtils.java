package com.github.jenya705.mcapi.util;

import lombok.experimental.UtilityClass;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Jenya705
 */
@UtilityClass
public class FutureUtils {

    public <T> Future<T> fromValue(T value) {
        return new AlreadyDoneFuture<>(value);
    }

    public <T> Future<T> fromException(Exception e) {
        return new AlreadyFailedFuture<>(new ExecutionException(e));
    }

    public <T> Future<T> fromCallable(Callable<T> callable) {
        try {
            return fromValue(callable.call());
        } catch (Exception e) {
            return fromException(e);
        }
    }

}

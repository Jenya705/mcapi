package com.github.jenya705.mcapi.util;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class AlreadyFailedFuture<T> implements Future<T> {

    private final ExecutionException executionException;

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        throw executionException;
    }

    @Override
    public T get(long timeout, @NotNull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        throw executionException;
    }
}

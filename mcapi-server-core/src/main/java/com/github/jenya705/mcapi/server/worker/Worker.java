package com.github.jenya705.mcapi.server.worker;

import com.github.jenya705.mcapi.server.util.ExceptionableRunnable;
import com.google.inject.ImplementedBy;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * @author Jenya705
 */
@ImplementedBy(ExecutorServiceWorker.class)
public interface Worker {

    <T> Future<T> invoke(Callable<T> supplier);

    void invoke(Runnable runnable);

    default void exceptionable(ExceptionableRunnable exceptionableRunnable) {
        exceptionable(exceptionableRunnable, it -> {});
    }

    default void exceptionable(ExceptionableRunnable exceptionableRunnable, Consumer<Exception> errorHandler) {
        invoke(() -> {
            try {
                exceptionableRunnable.run();
            } catch (Exception e) {
                errorHandler.accept(e);
            }
        });
    }

}

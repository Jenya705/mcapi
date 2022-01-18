package com.github.jenya705.mcapi.server.worker;

import com.google.inject.ImplementedBy;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author Jenya705
 */
@ImplementedBy(ExecutorServiceWorker.class)
public interface Worker {

    <T> Future<T> invoke(Callable<T> supplier);

    void invoke(Runnable runnable);

}

package com.github.jenya705.mcapi.server.worker;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author Jenya705
 */
public interface Worker {

    <T> Future<T> invoke(Callable<T> supplier);

    void invoke(Runnable runnable);

}

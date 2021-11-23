package com.github.jenya705.mcapi.worker;

import com.github.jenya705.mcapi.AbstractApplicationModule;
import com.github.jenya705.mcapi.OnInitializing;
import com.github.jenya705.mcapi.module.config.ConfigModule;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Jenya705
 */
public class ExecutorServiceWorker extends AbstractApplicationModule implements Worker {

    private ExecutorService threadPool;
    private ExecutorServiceWorkerConfig config;

    @OnInitializing(priority = 1)
    public void init() {
        config = new ExecutorServiceWorkerConfig(
                bean(ConfigModule.class)
                        .getConfig()
                        .required("worker")
        );
        threadPool = Executors.newFixedThreadPool(config.getThreads());
    }

    @Override
    public <T> Future<T> invoke(Callable<T> supplier) {
        return threadPool.submit(supplier);
    }

    @Override
    public void invoke(Runnable runnable) {
        threadPool.execute(runnable);
    }
}

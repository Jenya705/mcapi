package com.github.jenya705.mcapi.server.worker;

import com.github.jenya705.mcapi.server.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.OnInitializing;
import com.github.jenya705.mcapi.server.module.config.ConfigModule;
import com.github.jenya705.mcapi.server.util.FutureUtils;

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
        if (config.getThreads() > 0) {
            threadPool = Executors.newFixedThreadPool(config.getThreads());
        }
    }

    @Override
    public <T> Future<T> invoke(Callable<T> callable) {
        if (threadPool != null) {
            return threadPool.submit(callable);
        }
        return FutureUtils.fromCallable(callable);
    }

    @Override
    public void invoke(Runnable runnable) {
        if (threadPool != null) {
            threadPool.execute(runnable);
        }
        else {
            runnable.run();
        }
    }
}

package com.github.jenya705.mcapi.server.worker;

import com.github.jenya705.mcapi.server.module.config.ConfigModule;
import com.github.jenya705.mcapi.server.util.FutureUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jenya705
 */
@Singleton
public class ExecutorServiceWorker implements Worker {

    private static final AtomicInteger workerThreadsCounter = new AtomicInteger(1);

    private final ExecutorService threadPool;
    private final ExecutorServiceWorkerConfig config;

    @Inject
    public ExecutorServiceWorker(ConfigModule configModule) {
        config = new ExecutorServiceWorkerConfig(
                configModule
                        .getConfig()
                        .required("worker")
        );
        if (config.getThreads() > 0) {
            threadPool = Executors.newFixedThreadPool(config.getThreads(), runnable -> {
                Thread thread = new Thread(runnable);
                thread.setName("mcapi-worker-thread-" + workerThreadsCounter.getAndIncrement());
                thread.setDaemon(false);
                thread.setPriority(Thread.NORM_PRIORITY);
                return thread;
            });
        }
        else {
            threadPool = null;
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

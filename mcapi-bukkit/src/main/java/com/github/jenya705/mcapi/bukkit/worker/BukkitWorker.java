package com.github.jenya705.mcapi.bukkit.worker;

import com.github.jenya705.mcapi.bukkit.BukkitApplication;
import com.github.jenya705.mcapi.server.worker.Worker;
import com.google.inject.Inject;
import org.bukkit.Bukkit;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;

/**
 * Worker which is using bukkit scheduler
 *
 * @author Jenya705
 */
public class BukkitWorker implements Worker {

    private static final int workers = 4;

    private final BukkitApplication application;
    private final Queue<Runnable> tasks = new ConcurrentLinkedQueue<>();

    @Inject
    public BukkitWorker(BukkitApplication application) {
        this.application = application;
        for (int i = 0; i < workers; ++i) {
            Bukkit.getScheduler().runTaskTimerAsynchronously(
                    application, this::runnableQueueRunTask, 1, 1);
        }
    }

    private void runnableQueueRunTask() {
        Runnable current;
        while (Objects.nonNull(current = tasks.poll())) {
            current.run();
        }
    }

    @Override
    public <T> Future<T> invoke(Callable<T> supplier) {
        BukkitWorkerFuture<T> future = new BukkitWorkerFuture<>();
        future.setTask(Bukkit.getScheduler().runTaskAsynchronously(application, () -> {
            try {
                future.setValue(supplier.call());
            } catch (Throwable e) {
                future.setException(e);
            }
        }));
        return future;
    }

    @Override
    public void invoke(Runnable runnable) {
        tasks.add(runnable);
    }

}

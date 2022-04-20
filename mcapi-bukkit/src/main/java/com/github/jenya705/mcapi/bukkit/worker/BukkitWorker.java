package com.github.jenya705.mcapi.bukkit.worker;

import com.github.jenya705.mcapi.bukkit.BukkitApplication;
import com.github.jenya705.mcapi.bukkit.BukkitUtils;
import com.github.jenya705.mcapi.server.worker.Worker;
import com.google.inject.Inject;
import org.bukkit.Bukkit;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 *
 * Worker which is using bukkit scheduler
 *
 * @author Jenya705
 */
public class BukkitWorker implements Worker {

    private final BukkitApplication application;

    @Inject
    public BukkitWorker(BukkitApplication application) {
        this.application = application;
    }

    @Override
    public <T> Future<T> invoke(Callable<T> supplier) {
        BukkitWorkerFuture<T> future = new BukkitWorkerFuture<>();
        future.setTask(Bukkit.getScheduler().runTask(application, () -> {
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
        Bukkit.getScheduler().runTask(application, runnable);
    }
}

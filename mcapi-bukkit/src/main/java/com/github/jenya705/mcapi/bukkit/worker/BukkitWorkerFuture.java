package com.github.jenya705.mcapi.bukkit.worker;

import com.github.jenya705.mcapi.bukkit.BukkitThreadException;
import com.github.jenya705.mcapi.server.util.ValueContainer;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Jenya705
 */
@RequiredArgsConstructor
public class BukkitWorkerFuture<V> implements Future<V> {

    private BukkitTask task;
    private ValueContainer<V> value;
    private Throwable exception;

    public void setTask(BukkitTask task) {
        if (this.task != null) {
            throw new IllegalStateException("Task is already set");
        }
        this.task = task;
    }

    public void setException(Throwable exception) {
        checkResultExistent();
        synchronized (this) {
            this.exception = exception;
            notifyAll();
        }
    }

    public void setValue(V value) {
        checkResultExistent();
        synchronized (this) {
            this.value = new ValueContainer<>(value);
            notifyAll();
        }
    }

    private void checkResultExistent() {
        if (value != null || exception != null) {
            throw new IllegalStateException("Result is already given");
        }
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        if (value == null) {
            task.cancel();
            return true;
        }
        return false;
    }

    @Override
    public boolean isCancelled() {
        return task.isCancelled();
    }

    @Override
    public boolean isDone() {
        return value != null;
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        validateThread();
        if (value == null) {
            synchronized (this) {
                wait();
            }
        }
        return readyGet();
    }

    @Override
    public V get(long timeout, @NotNull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        validateThread();
        if (value == null) {
            synchronized (this) {
                wait(unit.toMillis(timeout));
            }
        }
        return readyGet();
    }

    private void validateThread() {
        if (Bukkit.isPrimaryThread()) {
            throw new BukkitThreadException("Can not block main thread");
        }
    }

    private V readyGet() throws ExecutionException {
        if (exception != null) {
            throw new ExecutionException(exception);
        }
        return value == null ? null : value.getValue();
    }

}

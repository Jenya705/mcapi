package com.github.jenya705.mcapi.reactor;

import com.github.jenya705.mcapi.util.Pair;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author Jenya705
 */
public class ReactorBlockingThread extends Thread {

    private final Queue<Pair<MonoSink<Object>, Object>> tasks = new ArrayDeque<>();

    public ReactorBlockingThread() {
        setName("Reactor-blocking-thread");
        setDaemon(true);
    }

    @Override
    public void run() {
        while (isAlive()) {
            Pair<MonoSink<Object>, Object> task = tasks.poll();
            if (task == null) {
                try {
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    // continue
                }
            }
            else if (task.getRight() instanceof Throwable) {
                task.getLeft().error((Throwable) task.getRight());
            }
            else {
                task.getLeft().success(task.getRight());
            }
        }
    }

    public <T> Mono<T> addTask(Mono<T> mono) {
        if (Thread.currentThread().equals(this)) {
            try {
                return Mono.justOrEmpty(mono.block());
            } catch (Throwable e) {
                return Mono.error(e);
            }
        }
        return Mono.create(sink ->
                mono
                        .doOnError(e -> add(sink, e))
                        .subscribe(value -> add(sink, value))
        );
    }

    @SuppressWarnings("unchecked")
    private <T> void add(MonoSink<T> sink, Object value) {
        tasks.add(new Pair<>((MonoSink<Object>) sink, value));
        synchronized (this) {
            notifyAll();
        }
    }

}

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

    private final Object waiter = new Object();

    public ReactorBlockingThread() {
        super("Reactor-blocking-thread");
        setDaemon(true);
    }

    @Override
    public void run() {
        while (isAlive()) {
            Pair<MonoSink<Object>, Object> task = tasks.poll();
            if (task == null) {
                try {
                    synchronized (waiter) {
                        waiter.wait();
                    }
                } catch (InterruptedException e) {
                    // continue
                }
            }
            else {
                task.getLeft().success(task.getRight());
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> Mono<T> addTask(Mono<T> mono) {
        if (Thread.currentThread().equals(this)) {
            return Mono.justOrEmpty(mono.block());
        }
        return Mono.create(sink ->
                mono.subscribe(value -> {
                    tasks.add(new Pair<>((MonoSink<Object>) sink, value));
                    synchronized (waiter) {
                        waiter.notifyAll();
                    }
                })
        );
    }
}

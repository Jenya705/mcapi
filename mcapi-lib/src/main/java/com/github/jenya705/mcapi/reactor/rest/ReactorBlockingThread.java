package com.github.jenya705.mcapi.reactor.rest;

import com.github.jenya705.mcapi.util.Pair;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author Jenya705
 */
public class ReactorBlockingThread extends Thread {

    private interface JustSink<T> {
        void next(T obj);

        void error(Throwable throwable);
    }

    @AllArgsConstructor
    private static class MonoJustSink<T> implements JustSink<T> {

        private final MonoSink<T> monoSink;

        @Override
        public void next(T obj) {
            monoSink.success(obj);
        }

        @Override
        public void error(Throwable throwable) {
            monoSink.error(throwable);
        }
    }

    @AllArgsConstructor
    private static class FluxJustSink<T> implements JustSink<T> {

        private final FluxSink<T> fluxSink;

        @Override
        public void next(T obj) {
            fluxSink.next(obj);
        }

        @Override
        public void error(Throwable throwable) {
            fluxSink.error(throwable);
        }
    }

    private final Queue<Pair<JustSink<Object>, Object>> tasks = new ArrayDeque<>();

    private boolean stopped = false;

    public ReactorBlockingThread() {
        setName("Reactor-blocking-thread");
        setDaemon(true);
    }

    @Override
    public void run() {
        while (isAlive() && !stopped) {
            Pair<JustSink<Object>, Object> task = tasks.poll();
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
                task.getLeft().next(task.getRight());
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

    public <T> Flux<T> addTask(Flux<T> flux) {
        return Flux.create(sink ->
                flux
                        .doOnError(e -> add(sink, e))
                        .subscribe(e -> add(sink, e))
        );
    }

    public void terminate() {
        stopped = true;
        sendNotify();
    }

    @SuppressWarnings("unchecked")
    private <T> void add(MonoSink<T> sink, Object value) {
        tasks.add(new Pair<>(
                new MonoJustSink<>((MonoSink<Object>) sink),
                value
        ));
        sendNotify();
    }

    @SuppressWarnings("unchecked")
    private <T> void add(FluxSink<T> sink, Object value) {
        tasks.add(new Pair<>(
                new FluxJustSink<>((FluxSink<Object>) sink),
                value
        ));
        sendNotify();
    }

    private void sendNotify() {
        synchronized (this) {
            notifyAll();
        }
    }

}

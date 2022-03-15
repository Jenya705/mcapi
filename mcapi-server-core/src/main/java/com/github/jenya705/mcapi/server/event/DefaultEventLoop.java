package com.github.jenya705.mcapi.server.event;

import com.github.jenya705.mcapi.server.util.CacheClassMap;
import com.github.jenya705.mcapi.server.worker.Worker;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @author Jenya705
 */
@Singleton
public class DefaultEventLoop implements EventLoop {

    private final Map<Class<?>, Collection<Consumer<Object>>> handlers = CacheClassMap.concurrent();

    private final Worker worker;
    private final Logger log;

    @Inject
    public DefaultEventLoop(Worker worker, Logger log) {
        this.worker = worker;
        this.log = log;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> EventLoop handler(Class<? extends T> clazz, Consumer<T> handler) {
        handlers.computeIfAbsent(clazz, k -> new ArrayList<>());
        handlers.get(clazz).add(obj -> handler.accept((T) obj));
        return this;
    }

    @Override
    public <T> EventLoop asyncHandler(Class<? extends T> clazz, Consumer<T> handler) {
        if (clazz.getAnnotation(NotAsyncEvent.class) != null) {
            throw new IllegalArgumentException("Event can not be handled asynchronously");
        }
        return handler(clazz, it ->
                worker.invoke(() -> handler.accept(it))
        );
    }

    @Override
    public void invoke(Object event) {
        handlers
                .getOrDefault(event.getClass(), Collections.emptyList())
                .forEach(objectConsumer -> {
                    try {
                        objectConsumer.accept(event);
                    } catch (Exception e) {
                        log.warn("Exception caught in event handler:", e);
                    }
                });
    }
}

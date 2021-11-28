package com.github.jenya705.mcapi.event;

import com.github.jenya705.mcapi.AbstractApplicationModule;
import com.github.jenya705.mcapi.util.CacheClassMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Jenya705
 */
public class DefaultEventLoop extends AbstractApplicationModule implements EventLoop {

    private final Map<Class<?>, Collection<Consumer<Object>>> handlers = new CacheClassMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public <T> EventLoop handler(Class<? extends T> clazz, Consumer<T> handler) {
        handlers.computeIfAbsent(clazz, k -> new ArrayList<>());
        handlers.get(clazz).add(obj -> handler.accept((T) obj));
        return this;
    }

    @Override
    public <T> EventLoop asyncHandler(Class<? extends T> clazz, Consumer<T> handler) {
        return handler(clazz, it ->
                worker()
                        .invoke(() ->
                                handler.accept(it)
                        )
        );
    }

    @Override
    public void invoke(Object event) {
        handlers
                .getOrDefault(event.getClass(), Collections.emptyList())
                .forEach(objectConsumer -> objectConsumer.accept(event));
    }
}

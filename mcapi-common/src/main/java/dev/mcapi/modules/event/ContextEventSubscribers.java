package dev.mcapi.modules.event;

import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

class ContextEventSubscribers implements EventSubscribers {

    private final Map<Object, Collection<FluxSink<Object>>> subscribers = new ConcurrentHashMap<>();
    private final Collection<FluxSink<Object>> withoutContext = new CopyOnWriteArrayList<>();

    @Override
    public <T> void addSubscriber(FluxSink<T> sink, Object context) {
        FluxSink<Object> delegate = new EventSubscribers.FluxSinkDelegate<>(sink);
        if (context == null) {
            withoutContext.add(delegate);
        } else {
            subscribers.computeIfAbsent(context, key -> new CopyOnWriteArrayList<>())
                    .add(delegate);
        }
    }

    @Override
    public <T> Mono<T> executeEvent(T event) {
        return Mono.just(event)
                .doOnNext(it -> {
                    // Guarantied by EventLoopImpl
                    Object context = ((ContextEvent) event).getContext();
                    if (context != null) {
                        subscribers.getOrDefault(context, Collections.emptyList())
                                .forEach(sink -> sink.next(it));
                    }
                    withoutContext.forEach(sink -> sink.next(it));
                });
    }
}

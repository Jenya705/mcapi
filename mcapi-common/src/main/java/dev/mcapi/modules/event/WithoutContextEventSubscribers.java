package dev.mcapi.modules.event;

import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

class WithoutContextEventSubscribers implements EventSubscribers {

    private final Collection<FluxSink<Object>> subscribers = new CopyOnWriteArrayList<>();

    @Override
    public <T> void addSubscriber(FluxSink<T> sink, @Nullable Object context) {
        subscribers.add(new EventSubscribers.FluxSinkDelegate<>(sink));
    }

    public <T> Mono<T> executeEvent(T event) {
        return Mono.just(event)
                .doOnNext(it -> subscribers.forEach(sink -> sink.next(it)));
    }
}

package dev.mcapi.modules.event;

import com.google.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class EventLoopImpl implements EventLoop {

    private final Map<Class<?>, EventSubscribers> subscribers = new ConcurrentHashMap<>();

    private static EventSubscribers createSubscribes(Class<?> clazz) {
        EventContext eventContextAnnotation = clazz.getDeclaredAnnotation(EventContext.class);
        boolean isContextEvent = clazz.isAssignableFrom(ContextEvent.class);
        if ((isContextEvent && eventContextAnnotation == null) ||
                (!isContextEvent && eventContextAnnotation != null)) {
            throw new IllegalArgumentException(
                    "Context event should have EventContext annotation and implement ContextEvent");
        }
        return isContextEvent ? new ContextEventSubscribers() : new WithoutContextEventSubscribers();
    }

    @Override
    public <T> Flux<T> subscribe(Class<T> clazz) {
        return subscribe(clazz, null);
    }

    @Override
    public <T> Flux<T> subscribe(Class<T> clazz, Object context) {
        return Flux.create(sink -> subscribers
                .computeIfAbsent(clazz, EventLoopImpl::createSubscribes)
                .addSubscriber(sink, context)
        );
    }

    @Override
    public <T> Mono<T> execute(T event) {
        EventSubscribers eventSubscribers = subscribers.get(event.getClass());
        return eventSubscribers == null ? Mono.empty() : eventSubscribers.executeEvent(event);
    }
}

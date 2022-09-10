package dev.mcapi.event;

import com.google.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Singleton
public class EventLoopImpl implements EventLoop {

    private interface EventNode<E> {
        Flux<E> subscribe(Object context);

        Mono<E> fire(Object event);
    }

    @SuppressWarnings("unchecked")
    private static class ContextEventNode<E extends ContextEvent<C>, C> implements EventNode<E> {
        private final Map<C, Collection<FluxSink<E>>> sinks = new ConcurrentHashMap<>();
        private final Collection<FluxSink<E>> inNonContext = new CopyOnWriteArrayList<>();

        @Override
        public Flux<E> subscribe(Object context) {
            return Flux.create(sink -> {
                if (context == null)
                    inNonContext.add(sink);
                else
                    sinks.computeIfAbsent((C) context, it -> new CopyOnWriteArrayList<>())
                            .add(sink);
            });
        }

        @Override
        public Mono<E> fire(Object event) {
            return Mono.just((E) event)
                    .doOnNext(it -> sinks
                            .get(it.getContext())
                            .forEach(sink -> sink.next(it))
                    )
                    .doOnNext(it -> inNonContext.forEach(sink -> sink.next(it)));
        }
    }

    private static class NonContextEventNode<E> implements EventNode<E> {
        private final Collection<FluxSink<E>> sinks = new CopyOnWriteArrayList<>();

        @Override
        public Flux<E> subscribe(Object context) {
            return Flux.create(sinks::add);
        }

        @Override
        @SuppressWarnings("unchecked")
        public Mono<E> fire(Object event) {
            return Mono.just((E) event)
                    .doOnNext(it -> sinks.forEach(sink -> sink.next(it)));
        }
    }

    private final Map<Class<?>, EventNode<?>> eventNodes = new ConcurrentHashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public <T> Flux<? extends T> subscribe(Class<T> eventClass) {
        return (Flux<? extends T>) eventNodes
                .computeIfAbsent(eventClass,
                        clazz -> isContextEvent(clazz) ?
                                new ContextEventNode<>() : new NonContextEventNode<>()
                ).subscribe(null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ContextEvent<C>, C> Flux<? extends T> subscribe(Class<T> eventClass, C context) {
        return (Flux<? extends T>) eventNodes
                .computeIfAbsent(eventClass, clazz -> new ContextEventNode<>())
                .subscribe(context);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Mono<T> fire(T event, Class<? super T> eventClass) {
        EventNode<?> node = eventNodes.get(eventClass);
        if (node == null) return Mono.just(event);
        return (Mono<T>) node.fire(event);
    }

    private boolean isContextEvent(Class<?> eventClass) {
        return ContextEvent.class.isAssignableFrom(eventClass);
    }

}

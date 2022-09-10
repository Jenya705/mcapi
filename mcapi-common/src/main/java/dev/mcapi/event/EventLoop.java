package dev.mcapi.event;

import com.google.inject.ImplementedBy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ImplementedBy(EventLoopImpl.class)
public interface EventLoop {

    <T> Flux<? extends T> subscribe(Class<T> eventClass);

    <T extends ContextEvent<C>, C> Flux<? extends T> subscribe(Class<T> eventClass, C context);

    @SuppressWarnings("unchecked")
    default <T> Mono<T> fire(T event) {
        return fire(event, (Class<? super T>) event.getClass());
    }

    <T> Mono<T> fire(T event, Class<? super T> eventClass);

}

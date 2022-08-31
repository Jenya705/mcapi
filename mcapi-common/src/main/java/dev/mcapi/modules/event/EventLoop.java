package dev.mcapi.modules.event;

import com.google.inject.ImplementedBy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ImplementedBy(EventLoopImpl.class)
public interface EventLoop {

    /**
     * Subscribes returned mono to given event
     *
     * @param clazz Class of event
     * @return Subscription flux
     * @param <T> Type of event
     */
    <T> Flux<T> subscribe(Class<T> clazz);

    /**
     * Subscribes returned mono to given event with given context
     *
     * @param clazz Class of event
     * @param context Context
     * @return Subscription flux
     * @param <T> Type of event
     * @throws IllegalArgumentException If given context class is not equals to event context class or given event class is not contextable event
     */
    <T> Flux<T> subscribe(Class<T> clazz, Object context);

    /**
     * Executes all subscribed event handlers
     *
     * @param event Event
     * @return Mono which will execute events
     * @param <T> Type of event
     */
    <T> Mono<T> execute(T event);

}

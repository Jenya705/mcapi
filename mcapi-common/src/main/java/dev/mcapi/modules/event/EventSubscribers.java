package dev.mcapi.modules.event;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reactor.core.Disposable;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.function.LongConsumer;

interface EventSubscribers {

    @RequiredArgsConstructor
    @SuppressWarnings("unchecked")
    class FluxSinkDelegate<T> implements FluxSink<Object> {

        private final FluxSink<T> fluxSink;


        @Override
        public @NotNull FluxSink<Object> next(Object o) {
            fluxSink.next((T) o);
            return this;
        }

        @Override
        public void complete() {
            fluxSink.complete();
        }

        @Override
        public void error(Throwable e) {
            fluxSink.error(e);
        }

        @Override
        public Context currentContext() {
            return fluxSink.currentContext();
        }

        @Override
        public long requestedFromDownstream() {
            return fluxSink.requestedFromDownstream();
        }

        @Override
        public boolean isCancelled() {
            return fluxSink.isCancelled();
        }

        @Override
        public FluxSink<Object> onRequest(LongConsumer consumer) {
            fluxSink.onRequest(consumer);
            return this;
        }

        @Override
        public FluxSink<Object> onCancel(Disposable d) {
            fluxSink.onCancel(d);
            return this;
        }

        @Override
        public FluxSink<Object> onDispose(Disposable d) {
            fluxSink.onDispose(d);
            return this;
        }
    }

    <T> void addSubscriber(FluxSink<T> sink, @Nullable Object context);

    <T> Mono<T> executeEvent(T event);

}

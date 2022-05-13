package com.github.jenya705.mcapi.server.ss.model.event;

import com.github.jenya705.mcapi.server.event.EventLoop;
import com.github.jenya705.mcapi.server.util.MultivaluedMap;
import com.github.jenya705.mcapi.server.worker.Worker;
import com.google.inject.Inject;

import java.util.function.Consumer;

/**
 * @author Jenya705
 */
public class ProxyModelEventHandlerNodeImpl implements ProxyModelEventHandlerNode {

    private final MultivaluedMap<String, Consumer<ProxyModelReceivedEvent>> handlers = MultivaluedMap.concurrent();
    private final Worker worker;

    @Inject
    public ProxyModelEventHandlerNodeImpl(Worker worker, EventLoop eventLoop) {
        this.worker = worker;
        eventLoop.handler(ProxyModelReceivedEvent.class, this::invoke);
    }

    @Override
    public ProxyModelEventHandlerNode handler(String type, Consumer<ProxyModelReceivedEvent> handler) {
        handlers.add(type, handler);
        return this;
    }

    @Override
    public ProxyModelEventHandlerNode asyncHandler(String type, Consumer<ProxyModelReceivedEvent> handler) {
        return handler(type, model ->
                worker.invoke(() -> handler.accept(model))
        );
    }

    @Override
    public void invoke(ProxyModelReceivedEvent event) {
        handlers.forEach(event.getModel().getType(), func -> func.accept(event));
    }
}

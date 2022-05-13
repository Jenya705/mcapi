package com.github.jenya705.mcapi.server.ss.model.event;

import com.github.jenya705.mcapi.server.ss.model.ProxyModel;

import java.util.function.Consumer;

/**
 * @author Jenya705
 */
public interface ProxyModelEventHandlerNode {

    ProxyModelEventHandlerNode handler(String type, Consumer<ProxyModelReceivedEvent> handler);

    ProxyModelEventHandlerNode asyncHandler(String type, Consumer<ProxyModelReceivedEvent> handler);

    void invoke(ProxyModelReceivedEvent event);

}

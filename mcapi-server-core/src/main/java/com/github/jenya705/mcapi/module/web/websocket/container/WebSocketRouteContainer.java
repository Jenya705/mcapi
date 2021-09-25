package com.github.jenya705.mcapi.module.web.websocket.container;

import com.github.jenya705.mcapi.module.web.websocket.WebSocketRouteHandler;

import java.util.Collection;

/**
 * @author Jenya705
 */
public interface WebSocketRouteContainer<T extends WebSocketContainerConnection> extends WebSocketRouteHandler {

    Collection<T> getConnections();
}

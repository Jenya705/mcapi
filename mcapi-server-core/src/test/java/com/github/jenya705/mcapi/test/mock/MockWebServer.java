package com.github.jenya705.mcapi.test.mock;

import com.github.jenya705.mcapi.Route;
import com.github.jenya705.mcapi.module.web.RouteHandler;
import com.github.jenya705.mcapi.module.web.WebServer;
import com.github.jenya705.mcapi.module.web.websocket.WebSocketRouteHandler;

public class MockWebServer implements WebServer {

    @Override
    public void addHandler(Route route, RouteHandler handler) {

    }

    @Override
    public void addWebSocketHandler(String uri, WebSocketRouteHandler handler) {

    }
}

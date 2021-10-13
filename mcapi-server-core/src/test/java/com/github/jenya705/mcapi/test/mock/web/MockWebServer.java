package com.github.jenya705.mcapi.test.mock.web;

import com.github.jenya705.mcapi.AbstractApplicationModule;
import com.github.jenya705.mcapi.module.web.RouteHandler;
import com.github.jenya705.mcapi.module.web.RoutePredicate;
import com.github.jenya705.mcapi.module.web.WebServer;
import com.github.jenya705.mcapi.module.web.websocket.WebSocketRouteHandler;

import java.util.function.Consumer;

public class MockWebServer extends AbstractApplicationModule implements WebServer {

    @Override
    public void addHandler(RoutePredicate requestPredicate, RouteHandler handler, boolean readBody) {

    }

    @Override
    public void addWebSocketHandler(String uri, WebSocketRouteHandler handler) {

    }

    public MockWebResponse test(Consumer<MockWebRequestBuilder> builderFunction, RouteHandler handler) throws Exception {
        MockWebResponse response = new MockWebResponse();
        MockWebRequestBuilder builder = MockWebRequestBuilder.builder(app());
        builderFunction.accept(builder);
        MockWebRequest request = builder.build();
        handler.handle(request, response);
        return response;
    }

}

package com.github.jenya705.mcapi.mock.web;

import com.github.jenya705.mcapi.AbstractApplicationModule;
import com.github.jenya705.mcapi.module.web.RouteHandler;
import com.github.jenya705.mcapi.module.web.RouteParameters;
import com.github.jenya705.mcapi.module.web.RoutePredicate;
import com.github.jenya705.mcapi.module.web.WebServer;
import com.github.jenya705.mcapi.module.web.websocket.WebSocketRouteHandler;
import com.github.jenya705.mcapi.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MockWebServer extends AbstractApplicationModule implements WebServer {

    private final List<Pair<RoutePredicate, RouteHandler>> routes = new ArrayList<>();

    @Override
    public void addHandler(RoutePredicate requestPredicate, RouteHandler handler, boolean readBody) {
        routes.add(new Pair<>(requestPredicate, handler));
    }

    @Override
    public void addWebSocketHandler(String uri, WebSocketRouteHandler handler) {

    }

    public MockWebResponse test(Consumer<MockWebRequestBuilder> builderConsumer) throws Exception {
        MockWebRequestBuilder webRequestBuilder = MockWebRequestBuilder.builder(app());
        builderConsumer.accept(webRequestBuilder);
        MockWebRequest webRequest = webRequestBuilder.build();
        for (Pair<RoutePredicate, RouteHandler> route: routes) {
            RoutePredicate routePredicate = route.getLeft();
            if (routePredicate.apply(webRequest.getMethod(), webRequest.getUri())) {
                RouteHandler handler = route.getRight();
                if (routePredicate instanceof RouteParameters) {
                    RouteParameters routeParameters = (RouteParameters) routePredicate;
                    webRequest.joinParams(routeParameters.getParameters(webRequest.getUri()));
                }
                MockWebResponse response = new MockWebResponse();
                handler.handle(webRequest, response);
                return response;
            }
        }
    }

}

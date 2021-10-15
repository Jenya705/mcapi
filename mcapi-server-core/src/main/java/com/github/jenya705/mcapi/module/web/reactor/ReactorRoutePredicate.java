package com.github.jenya705.mcapi.module.web.reactor;

import com.github.jenya705.mcapi.module.web.RouteParameters;
import com.github.jenya705.mcapi.module.web.RoutePredicate;
import lombok.AllArgsConstructor;
import reactor.netty.http.server.HttpServerRequest;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

@AllArgsConstructor
public class ReactorRoutePredicate implements Predicate<HttpServerRequest>, Function<Object, Map<String, String>> {

    private final RoutePredicate routePredicate;

    @Override
    public Map<String, String> apply(Object key) {
        if (routePredicate instanceof RouteParameters) {
            Map<String, String> headers = ((RouteParameters) routePredicate).getParameters(key.toString());
            return headers == null || headers.isEmpty() ? null : headers;
        }
        return null;
    }

    @Override
    public boolean test(HttpServerRequest httpServerRequest) {
        return test(routePredicate, httpServerRequest);
    }

    public static boolean test(RoutePredicate routeHandler, HttpServerRequest request) {
        return routeHandler.apply(
                ReactorNettyUtils.wrap(request.method()),
                request.uri()
        );
    }

}

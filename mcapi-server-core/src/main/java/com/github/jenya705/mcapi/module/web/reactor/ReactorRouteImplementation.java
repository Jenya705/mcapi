package com.github.jenya705.mcapi.module.web.reactor;

import com.github.jenya705.mcapi.module.web.RouteHandler;
import com.github.jenya705.mcapi.module.web.RoutePredicate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReactorRouteImplementation {

    private final RoutePredicate routePredicate;
    private final RouteHandler routeHandler;
    private final boolean readBody;

}

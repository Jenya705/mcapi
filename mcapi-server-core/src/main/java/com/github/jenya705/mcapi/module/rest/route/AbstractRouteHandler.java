package com.github.jenya705.mcapi.module.rest.route;

import com.github.jenya705.mcapi.AbstractApplicationModule;
import com.github.jenya705.mcapi.OnStartup;
import com.github.jenya705.mcapi.Route;
import com.github.jenya705.mcapi.module.web.RouteHandler;
import com.github.jenya705.mcapi.module.web.WebServer;

/**
 * @author Jenya705
 */
public abstract class AbstractRouteHandler extends AbstractApplicationModule implements RouteHandler {

    private final Route route;

    public AbstractRouteHandler(Route route) {
        this.route = route;
    }

    @OnStartup
    public void addSelfRouteHandler() {
        bean(WebServer.class).addHandler(route, this);
    }

}

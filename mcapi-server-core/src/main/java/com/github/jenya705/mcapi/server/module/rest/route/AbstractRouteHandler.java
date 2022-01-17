package com.github.jenya705.mcapi.server.module.rest.route;

import com.github.jenya705.mcapi.Route;
import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.OnStartup;
import com.github.jenya705.mcapi.server.ignore.Ignorable;
import com.github.jenya705.mcapi.server.module.web.RouteHandler;
import com.github.jenya705.mcapi.server.module.web.WebServer;

/**
 * @author Jenya705
 */
@Ignorable
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

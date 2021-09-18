package com.github.jenya705.mcapi.module.web;

import com.github.jenya705.mcapi.Route;

/**
 * @author Jenya705
 */
public interface WebServer {

    void addHandler(Route route, RouteHandler handler);

}

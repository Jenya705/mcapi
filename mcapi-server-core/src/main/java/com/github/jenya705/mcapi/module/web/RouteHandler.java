package com.github.jenya705.mcapi.module.web;

/**
 * @author Jenya705
 */
@FunctionalInterface
public interface RouteHandler {

    void handle(Request request, Response response);

}
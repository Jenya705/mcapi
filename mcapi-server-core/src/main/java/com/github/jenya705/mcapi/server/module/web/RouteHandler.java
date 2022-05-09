package com.github.jenya705.mcapi.server.module.web;

import reactor.core.publisher.Mono;

/**
 * @author Jenya705
 */
@FunctionalInterface
public interface RouteHandler {

    Mono<Response> handle(Request request);

}

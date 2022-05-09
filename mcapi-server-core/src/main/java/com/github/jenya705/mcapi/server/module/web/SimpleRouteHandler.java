package com.github.jenya705.mcapi.server.module.web;

import com.github.jenya705.mcapi.server.util.ReactorUtils;
import reactor.core.publisher.Mono;

/**
 * @author Jenya705
 */
@FunctionalInterface
public interface SimpleRouteHandler extends RouteHandler {

    @Override
    default Mono<Response> handle(Request request) {
        return ReactorUtils.mono(() -> simpleHandle(request));
    }

    Response simpleHandle(Request request) throws Exception;

}

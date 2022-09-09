package dev.mcapi.web;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface WebHandler {

    Mono<WebResponse> respond(WebRequest request);

}

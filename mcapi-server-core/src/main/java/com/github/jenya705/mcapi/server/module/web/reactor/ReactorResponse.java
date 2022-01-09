package com.github.jenya705.mcapi.server.module.web.reactor;

import com.github.jenya705.mcapi.server.module.web.Response;
import lombok.Getter;
import reactor.netty.http.server.HttpServerResponse;

/**
 * @author Jenya705
 */
public class ReactorResponse implements Response {

    private final HttpServerResponse response;

    @Getter
    private Object body;

    public ReactorResponse(HttpServerResponse response) {
        this.response = response;
    }

    @Override
    public Response status(int status) {
        response.status(status);
        return this;
    }

    @Override
    public Response header(String key, String value) {
        response.header(key, value);
        return this;
    }

    @Override
    public Response body(Object body) {
        this.body = body;
        return this;
    }

    @Override
    public Response contentType(String type) {
        response.header("Content-type", type);
        return this;
    }
}

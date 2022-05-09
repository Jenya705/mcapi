package com.github.jenya705.mcapi.server.module.web;

import com.github.jenya705.mcapi.ApiError;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * Response class.
 *
 * Old interface was changed due to mono issues.
 *
 * @author Jenya705
 */
@Getter
public final class Response {

    private final Map<String, String> headers = new HashMap<>();
    private int status;
    private Object body;

    public static Response create() {
        return new Response();
    }

    public Response noContent() {
        return status(204);
    }

    public Response ok(Object body) {
        return body(body).status(200);
    }

    public Response accepted() {
        return status(202);
    }

    public Response notFound() {
        return status(404);
    }

    public Response error(Throwable exception) {
        return error(ApiError.raw(exception));
    }

    public Response error(ApiError error) {
        return body(error).status(error.getStatusCode());
    }

    public Response status(int status) {
        this.status = status;
        return this;
    }

    public Response contentType(String value) {
        return header("Content-Type", value);
    }

    public Response header(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public Response body(Object body) {
        this.body = body;
        return this;
    }

}

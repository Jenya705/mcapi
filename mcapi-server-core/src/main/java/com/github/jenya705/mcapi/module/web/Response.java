package com.github.jenya705.mcapi.module.web;

/**
 * @author Jenya705
 */
public interface Response {

    Response status(int status);

    Response header(String key, String value);

    Response body(Object body);

    Response contentType(String type);

    default Response noContent() {
        return status(204).body(null);
    }

    default Response ok(Object body) {
        return status(200).body(body);
    }

}

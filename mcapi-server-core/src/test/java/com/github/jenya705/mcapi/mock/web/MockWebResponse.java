package com.github.jenya705.mcapi.mock.web;

import com.github.jenya705.mcapi.module.web.Response;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class MockWebResponse implements Response {

    private final Map<String, String> headers = new HashMap<>();
    private int status;
    private Object body;
    private String contentType;

    @Override
    public Response status(int status) {
        this.status = status;
        return this;
    }

    @Override
    public Response header(String key, String value) {
        headers.put(key, value);
        return this;
    }

    @Override
    public Response body(Object body) {
        this.body = body;
        return this;
    }

    @Override
    public Response contentType(String type) {
        this.contentType = contentType;
        return this;
    }
}

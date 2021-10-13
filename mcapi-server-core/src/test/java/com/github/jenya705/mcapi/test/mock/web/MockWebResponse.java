package com.github.jenya705.mcapi.test.mock.web;

import com.github.jenya705.mcapi.module.web.Response;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class MockWebResponse implements Response {

    private int status;
    private Map<String, String> headers;
    private Object body;
    private String contentType;

    @Override
    public Response status(int status) {
        this.status = status;
        return this;
    }

    @Override
    public Response header(String key, String value) {
        if (headers == null) headers = new HashMap<>();
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
        contentType = type;
        return this;
    }
}

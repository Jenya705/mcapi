package com.github.jenya705.mcapi.mock.web;

import com.github.jenya705.mcapi.HttpMethod;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.module.mapper.Mapper;

import java.util.HashMap;
import java.util.Map;

public class MockWebRequestBuilder {

    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, String> params = new HashMap<>();
    private String uri;
    private HttpMethod method;
    private String body;

    private final Mapper mapper;

    private MockWebRequestBuilder(ServerApplication application) {
        mapper = application.getBean(Mapper.class);
    }

    public static MockWebRequestBuilder builder(ServerApplication application) {
        return new MockWebRequestBuilder(application);
    }

    public MockWebRequestBuilder uri(String uri) {
        this.uri = uri;
        return this;
    }

    public MockWebRequestBuilder method(HttpMethod method) {
        this.method = method;
        return this;
    }

    public MockWebRequestBuilder body(String json) {
        body = json;
        return this;
    }

    public MockWebRequestBuilder body(Object obj) {
        body = mapper.asJson(obj);
        return this;
    }

    public MockWebRequestBuilder header(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public MockWebRequestBuilder param(String key, String value) {
        params.put(key, value);
        return this;
    }

    public MockWebRequest build() {
        return new MockWebRequest(
                params, headers, uri, method, body, mapper
        );
    }

}

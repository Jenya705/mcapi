package com.github.jenya705.mcapi.test.mock.web;

import com.github.jenya705.mcapi.HttpMethod;
import com.github.jenya705.mcapi.ServerApplication;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MockWebRequestBuilder {

    private String uri;
    private HttpMethod method;
    private Object body;
    private Map<String, String> headers;
    private Map<String, String> params;

    private final ServerApplication application;

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

    public MockWebRequestBuilder get() {
        return method(HttpMethod.GET);
    }

    public MockWebRequestBuilder post() {
        return method(HttpMethod.POST);
    }

    public MockWebRequestBuilder delete() {
        return method(HttpMethod.DELETE);
    }

    public MockWebRequestBuilder put() {
        return method(HttpMethod.PUT);
    }

    public MockWebRequestBuilder patch() {
        return method(HttpMethod.PATCH);
    }

    public MockWebRequestBuilder body(Object body) {
        this.body = body;
        return this;
    }

    public MockWebRequestBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public MockWebRequestBuilder header(String key, String value) {
        if (headers == null) headers = new HashMap<>();
        headers.put(key, value);
        return this;
    }

    public MockWebRequestBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public MockWebRequestBuilder param(String key, String value) {
        if (params == null) params = new HashMap<>();
        params.put(key, value);
        return this;
    }

    public MockWebRequest build() {
        if (uri == null || method == null) {
            throw new IllegalStateException("Uri or method fields are null");
        }
        return new MockWebRequest(
                uri,
                method,
                body,
                headers == null ? new HashMap<>() : headers,
                params == null ? new HashMap<>() : params,
                application
        );
    }

}

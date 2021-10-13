package com.github.jenya705.mcapi.test.mock.web;

import com.github.jenya705.mcapi.HttpMethod;
import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.module.mapper.Mapper;
import com.github.jenya705.mcapi.module.web.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.Optional;

public class MockWebRequest implements Request {

    @Getter
    private final String uri;
    @Getter
    private final HttpMethod method;

    private final Object body;
    private final Map<String, String> headers;
    private final Map<String, String> params;

    private final Mapper mapper;

    public MockWebRequest(String uri, HttpMethod method, Object body, Map<String, String> headers, Map<String, String> params, ServerApplication application) {
        this.uri = uri;
        this.method = method;
        this.body = body;
        this.headers = headers;
        this.params = params;
        mapper = application.getBean(Mapper.class);
    }

    @Override
    public <T> Optional<T> param(String key, Class<? extends T> clazz) {
        return Optional.empty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Optional<T> body(Class<? extends T> clazz) {
        return Optional.ofNullable((T) body);
    }

    @Override
    public <T> Optional<T> header(String key, Class<? extends T> clazz) {
        return headers.containsKey(key) ?
                Optional.of(mapper.fromRaw(headers.get(key), clazz)):
                Optional.empty();
    }
}

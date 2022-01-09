package com.github.jenya705.mcapi.mock.web;

import com.github.jenya705.mcapi.HttpMethod;
import com.github.jenya705.mcapi.server.ServerApplication;
import com.github.jenya705.mcapi.server.module.mapper.Mapper;
import com.github.jenya705.mcapi.server.module.web.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class MockWebRequest implements Request {

    public static MockWebRequestBuilder builder(ServerApplication application) {
        return MockWebRequestBuilder.builder(application);
    }

    private final Map<String, String> params;
    private final Map<String, String> headers;
    @Getter
    private final String uri;
    @Getter
    private final HttpMethod method;
    private final String body;

    private final Mapper mapper;

    @Override
    public <T> Optional<T> param(String key, Class<? extends T> clazz) {
        return params.containsKey(key) ?
                Optional.of(mapper.fromRaw(params.get(key), clazz)) :
                Optional.empty();
    }

    @Override
    public <T> Optional<T> body(Class<? extends T> clazz) {
        return body != null ?
                Optional.ofNullable(mapper.fromJson(body, clazz)) :
                Optional.empty();
    }

    @Override
    public <T> Optional<T> header(String key, Class<? extends T> clazz) {
        return headers.containsKey(key) ?
                Optional.of(mapper.fromRaw(headers.get(key), clazz)) :
                Optional.empty();
    }

    public void joinParams(Map<String, String> params) {
        this.params.putAll(params);
    }

}

package com.github.jenya705.mcapi.mock.web;

import com.github.jenya705.mcapi.HttpMethod;
import com.github.jenya705.mcapi.module.mapper.Mapper;
import com.github.jenya705.mcapi.module.web.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class MockWebRequest implements Request {

    private final Map<String, String> params;
    private final Map<String, String> headers;
    @Getter
    private final String uri;
    @Getter
    private final HttpMethod method;
    private final Object body;

    private final Mapper mapper;

    @Override
    public <T> Optional<T> param(String key, Class<? extends T> clazz) {
        return params.containsKey(key) ?
                Optional.of(mapper.fromRaw(params.get(key), clazz)) :
                Optional.empty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Optional<T> body(Class<? extends T> clazz) {
        return Optional.ofNullable((T) body);
    }

    @Override
    public <T> Optional<T> header(String key, Class<? extends T> clazz) {
        return headers.containsKey(key) ?
                Optional.of(mapper.fromRaw(headers.get(key), clazz)) :
                Optional.empty();
    }
}

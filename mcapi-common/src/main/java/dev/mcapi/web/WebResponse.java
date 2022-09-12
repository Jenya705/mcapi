package dev.mcapi.web;

import dev.mcapi.mapper.Mapper;

import java.util.Map;

public interface WebResponse {

    static WebResponse create() {
        return null;
    }

    WebResponse status(int code);

    WebResponse body(Object body);

    WebResponse rawBody(String body);

    WebResponse header(String key, String value);

    int status();

    String  buildBody(Mapper mapper);

    Map<String, String> headers();

}

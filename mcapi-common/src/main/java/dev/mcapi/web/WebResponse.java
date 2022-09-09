package dev.mcapi.web;

import dev.mcapi.mapper.ObjectMapper;

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

    String  buildBody(ObjectMapper mapper);

    Map<String, String> headers();

}

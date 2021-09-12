package com.github.jenya705.mcapi;

import io.netty.handler.codec.http.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jenya705
 */
@Getter
@AllArgsConstructor(staticName = "of")
public class Route {

    public static Route get(String uri) {
        return Route.of(HttpMethod.GET, uri);
    }

    public static Route post(String uri) {
        return Route.of(HttpMethod.POST, uri);
    }

    public static Route put(String uri) {
        return Route.of(HttpMethod.PUT, uri);
    }

    public static Route delete(String uri) {
        return Route.of(HttpMethod.DELETE, uri);
    }

    public static Route patch(String uri) {
        return Route.of(HttpMethod.PATCH, uri);
    }

    private final HttpMethod httpMethod;
    private final String uri;

}

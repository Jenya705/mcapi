package com.github.jenya705.mcapi;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jenya705
 */
@Getter
@AllArgsConstructor(staticName = "of")
public class Route {

    public static Route post(String url) {
        return Route.of(HttpMethod.POST, url);
    }

    public static Route get(String url) {
        return Route.of(HttpMethod.GET, url);
    }

    public static Route put(String url) {
        return Route.of(HttpMethod.PUT, url);
    }

    public static Route patch(String url) {
        return Route.of(HttpMethod.PATCH, url);
    }

    public static Route delete(String url) {
        return Route.of(HttpMethod.DELETE, url);
    }

    private final HttpMethod method;
    private final String url;

}

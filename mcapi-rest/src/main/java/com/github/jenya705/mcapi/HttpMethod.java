package com.github.jenya705.mcapi;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jenya705
 */
@Getter
@AllArgsConstructor
public enum HttpMethod {

    POST("POST", true),
    GET("GET", false),
    PUT("PUT", true),
    DELETE("DELETE", true),
    PATCH("PATCH", true);

    private final String name;
    private final boolean withBody;
}

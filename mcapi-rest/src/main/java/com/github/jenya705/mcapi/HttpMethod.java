package com.github.jenya705.mcapi;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jenya705
 */
@Getter
@AllArgsConstructor
public enum HttpMethod {

    POST("POST"),
    GET("GET"),
    PUT("PUT"),
    DELETE("DELETE"),
    PATCH("PATCH");

    private final String name;

}

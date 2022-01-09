package com.github.jenya705.mcapi.server.module.web;

import com.github.jenya705.mcapi.HttpMethod;

@FunctionalInterface
public interface RoutePredicate {

    boolean apply(HttpMethod httpMethod, String uri);

}

package com.github.jenya705.mcapi.module.web;

import java.util.Map;

@FunctionalInterface
public interface RouteParameters {

    Map<String, String> getParameters(String uri);

}

package com.github.jenya705.mcapi.module.web;

import java.util.Map;

public interface RouteParameters {

    Map<String, String> getParameters(String uri);

}

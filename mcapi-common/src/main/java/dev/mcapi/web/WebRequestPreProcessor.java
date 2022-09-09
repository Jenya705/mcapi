package dev.mcapi.web;

import java.util.Map;

public interface WebRequestPreProcessor {

    boolean isPassed(WebRequest request);

    void fillParameters(WebRequest request, Map<String, String> parameters);

}

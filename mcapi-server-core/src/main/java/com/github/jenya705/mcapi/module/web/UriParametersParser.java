package com.github.jenya705.mcapi.module.web;

import com.github.jenya705.mcapi.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UriParametersParser {

    private final List<Pair<String, Integer>> params;

    public UriParametersParser(String uri) {
        String[] uriElements = uri.split("/");
        params = new ArrayList<>();
        int current = -1;
        for (String uriElement : uriElements) {
            current++;
            if (!uriElement.startsWith("{") || !uriElement.endsWith("}")) continue;
            params.add(new Pair<>(uriElement.substring(1, uriElement.length() - 2), current));
        }
    }

    public Map<String, String> get(String uri) {
        String[] uriElements = uri.split("/");
        Map<String, String> result = new HashMap<>();
        for (Pair<String, Integer> uriParamTemplate: params) {
            result.put(uriParamTemplate.getLeft(), uriElements[uriParamTemplate.getRight()]);
        }
        return result;
    }

}

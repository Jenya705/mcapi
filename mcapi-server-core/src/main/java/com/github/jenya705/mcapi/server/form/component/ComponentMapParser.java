package com.github.jenya705.mcapi.server.form.component;

import com.github.jenya705.mcapi.server.form.FormComponent;

import java.util.Map;
import java.util.function.Function;

/**
 * @author Jenya705
 */
public interface ComponentMapParser {

    void addType(String type, Function<Map<String, Object>, FormComponent> function);

    FormComponent buildComponent(Map<String, Object> obj);
}

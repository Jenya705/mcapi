package com.github.jenya705.mcapi.server.form.component;

import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.form.FormComponent;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Jenya705
 */
public class ComponentMapParserImpl extends AbstractApplicationModule implements ComponentMapParser {

    public Map<String, Function<Map<String, Object>, FormComponent>> formComponentBuilders = new HashMap<>();

    public ComponentMapParserImpl() {
        addType("button", map -> ButtonComponent.of(
                String.valueOf(map.get("text")),
                String.valueOf(map.get("onClick"))
        ));
        addType("content", map -> ContentComponent.of(
                String.valueOf(map.get("content"))
        ));
        addType("newline", map -> NewLineComponent.get());
        addType("title", map -> TitleComponent.of(
                String.valueOf(map.get("title"))
        ));
    }

    @Override
    public void addType(String type, Function<Map<String, Object>, FormComponent> function) {
        formComponentBuilders.put(type.toLowerCase(Locale.ROOT), function);
    }

    @Override
    public FormComponent buildComponent(Map<String, Object> obj) {
        if (!obj.containsKey("type")) {
            throw new IllegalArgumentException("Map does not contains type");
        }
        return formComponentBuilders.get(String.valueOf(obj.get("type"))).apply(obj);
    }
}

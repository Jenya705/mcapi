package com.github.jenya705.mcapi.form.component;

import com.github.jenya705.mcapi.form.FormComponent;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Jenya705
 */
@UtilityClass
public class ComponentMapParser {

    public Map<String, Function<Map<String, Object>, FormComponent>> formComponentBuilders = new HashMap<>();

    static {
        formComponentBuilders.put("button", map -> ButtonComponent.of(
                String.valueOf(map.get("text")),
                String.valueOf(map.get("onClick"))
        ));
        formComponentBuilders.put("content", map -> ContentComponent.of(
                String.valueOf(map.get("content"))
        ));
        formComponentBuilders.put("newline", map -> NewLineComponent.get());
        formComponentBuilders.put("title", map -> TitleComponent.of(
                String.valueOf(map.get("title"))
        ));
    }

    public FormComponent buildComponent(Map<String, Object> obj) {
        if (!obj.containsKey("type")) {
            throw new IllegalArgumentException("Map does not contains type");
        }
        return formComponentBuilders.get(String.valueOf(obj.get("type"))).apply(obj);
    }

}

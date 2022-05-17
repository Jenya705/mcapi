package com.github.jenya705.mcapi.server.form.component;

import com.github.jenya705.mcapi.server.form.FormBuilder;
import com.github.jenya705.mcapi.server.form.FormComponent;
import com.github.jenya705.mcapi.server.util.ListUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * @author Jenya705
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ListComponent implements FormComponent {

    private final FormComponent[] components;

    public static ListComponent of(FormComponent... components) {
        return new ListComponent(components);
    }

    public static ListComponent joining(FormComponent[]... components) {
        return new ListComponent(ListUtils.joinArray(FormComponent[]::new, components));
    }

    @Override
    public void apply(FormBuilder builder) {
        for (FormComponent component : components) builder.component(component);
    }
}

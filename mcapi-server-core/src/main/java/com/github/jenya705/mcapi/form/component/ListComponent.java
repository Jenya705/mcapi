package com.github.jenya705.mcapi.form.component;

import com.github.jenya705.mcapi.form.FormBuilder;
import com.github.jenya705.mcapi.form.FormComponent;
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
        int len = 0;
        for (FormComponent[] array: components) len += array.length;
        FormComponent[] newArray = new FormComponent[len];
        int i = 0;
        for (FormComponent[] array: components) {
            for (FormComponent component : array) {
                newArray[i++] = component;
            }
        }
        return new ListComponent(newArray);
    }

    @Override
    public void apply(FormBuilder builder) {
        for (FormComponent component: components) builder.component(component);
    }
}

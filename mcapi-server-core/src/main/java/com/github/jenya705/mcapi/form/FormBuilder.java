package com.github.jenya705.mcapi.form;

import java.util.Map;

/**
 * @author Jenya705
 */
public interface FormBuilder {

    FormBuilder title(String title);

    FormBuilder content(String content);

    FormBuilder button(String text, String onClick);

    FormBuilder newLine();

    Form build();

    default FormBuilder component(FormComponent component) {
        component.apply(this);
        return this;
    }

    default FormBuilder components(String[] components, Map<String, FormComponent> componentsMapping) {
        for (String component: components) {
            FormComponent currentComponent = componentsMapping.getOrDefault(component, null);
            if (currentComponent != null) component(currentComponent);
            else content(component);
        }
        return this;
    }

}

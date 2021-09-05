package com.github.jenya705.mcapi.form;

/**
 * @author Jenya705
 */
@FunctionalInterface
public interface FormComponent {

    void apply(FormBuilder builder);
}

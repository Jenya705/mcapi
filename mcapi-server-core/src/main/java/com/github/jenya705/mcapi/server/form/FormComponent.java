package com.github.jenya705.mcapi.server.form;

/**
 * @author Jenya705
 */
@FunctionalInterface
public interface FormComponent {

    void apply(FormBuilder builder);
}

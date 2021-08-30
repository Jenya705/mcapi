package com.github.jenya705.mcapi.form.component;

import com.github.jenya705.mcapi.form.FormBuilder;
import com.github.jenya705.mcapi.form.FormComponent;
import lombok.AllArgsConstructor;

/**
 * @author Jenya705
 */
@AllArgsConstructor(staticName = "of")
public class ButtonComponent implements FormComponent {

    private final String text;
    private final String onClick;

    @Override
    public void apply(FormBuilder builder) {
        builder.button(text, onClick);
    }
}

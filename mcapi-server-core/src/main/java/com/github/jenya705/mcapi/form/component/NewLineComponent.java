package com.github.jenya705.mcapi.form.component;

import com.github.jenya705.mcapi.form.FormBuilder;
import com.github.jenya705.mcapi.form.FormComponent;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NewLineComponent implements FormComponent {

    private static final NewLineComponent component = new NewLineComponent();

    public static NewLineComponent get() {
        return component;
    }

    @Override
    public void apply(FormBuilder builder) {
        builder.newLine();
    }
}

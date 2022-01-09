package com.github.jenya705.mcapi.server.form.component;

import com.github.jenya705.mcapi.server.form.FormBuilder;
import com.github.jenya705.mcapi.server.form.FormComponent;
import lombok.AllArgsConstructor;

/**
 * @author Jenya705
 */
@AllArgsConstructor(staticName = "of")
public class TitleComponent implements FormComponent {

    private final String title;

    @Override
    public void apply(FormBuilder builder) {
        builder.title(title);
    }
}

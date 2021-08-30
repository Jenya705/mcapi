package com.github.jenya705.mcapi.form;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;

/**
 * @author Jenya705
 */
public class JavaFormBuilder implements FormBuilder {

    private Component message = Component.empty();

    @Override
    public FormBuilder title(String title) {
        message = message.append(Component.text(title));
        return this;
    }

    @Override
    public FormBuilder content(String content) {
        message = message.append(Component.text(content));
        return null;
    }

    @Override
    public FormBuilder button(String text, String onClick) {
        message = message.append(Component
                .text(text)
                .clickEvent(ClickEvent.runCommand(onClick))
        );
        return this;
    }

    @Override
    public FormBuilder newLine() {
        message = message.append(Component.newline());
        return null;
    }

    @Override
    public Form build() {
        return new JavaForm(message);
    }
}

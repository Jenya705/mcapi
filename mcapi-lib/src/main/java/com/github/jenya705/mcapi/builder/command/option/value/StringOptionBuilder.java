package com.github.jenya705.mcapi.builder.command.option.value;

import com.github.jenya705.mcapi.builder.command.option.CommandValueOptionBuilder;
import com.github.jenya705.mcapi.command.types.StringOption;

/**
 * @author Jenya705
 */
public class StringOptionBuilder extends CommandValueOptionBuilder<StringOptionBuilder, StringOption> {

    @Override
    public StringOption build() {
        validate();
        return new StringOption(
                getName(),
                isRequired(),
                getTab(),
                isOnlyFromTab()
        );
    }
}

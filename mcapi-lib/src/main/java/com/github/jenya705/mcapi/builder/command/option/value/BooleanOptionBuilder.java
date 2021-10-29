package com.github.jenya705.mcapi.builder.command.option.value;

import com.github.jenya705.mcapi.builder.command.option.CommandValueOptionBuilder;
import com.github.jenya705.mcapi.command.types.BooleanOption;

/**
 * @author Jenya705
 */
public class BooleanOptionBuilder extends CommandValueOptionBuilder<BooleanOptionBuilder, BooleanOption> {



    @Override
    public BooleanOption build() {
        return new BooleanOption(
                getName(),
                isRequired(),
                getTab(),
                isOnlyFromTab()
        );
    }
}

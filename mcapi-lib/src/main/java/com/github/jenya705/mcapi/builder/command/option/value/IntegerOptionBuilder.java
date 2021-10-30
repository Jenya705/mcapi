package com.github.jenya705.mcapi.builder.command.option.value;

import com.github.jenya705.mcapi.builder.command.option.CommandValueOptionBuilder;
import com.github.jenya705.mcapi.command.types.IntegerOption;

/**
 * @author Jenya705
 */
public class IntegerOptionBuilder extends CommandValueOptionBuilder<IntegerOptionBuilder, IntegerOption> {

    private int max = Integer.MAX_VALUE;
    private int min = Integer.MIN_VALUE;

    public IntegerOptionBuilder min(int min) {
        this.min = min;
        return this;
    }

    public IntegerOptionBuilder max(int max) {
        this.max = max;
        return this;
    }

    @Override
    public IntegerOptionBuilder validate() {
        super.validate();
        if (min >= max) {
            throw new IllegalArgumentException("Min is not less than max");
        }
        return this;
    }

    @Override
    public IntegerOption build() {
        validate();
        return new IntegerOption(
                getName(),
                isRequired(),
                getTab(),
                isOnlyFromTab(),
                max, min
        );
    }
}

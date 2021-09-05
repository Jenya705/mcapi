package com.github.jenya705.mcapi.command.types;

import lombok.Getter;

/**
 * @author Jenya705
 */
@Getter
public class IntegerOption extends AbstractValueOption {

    public static final String type = "integer";

    private final int max;
    private final int min;

    public IntegerOption(String name, boolean required, Object tab, boolean onlyFromTab, int max, int min) {
        super(name, required, tab, onlyFromTab);
        this.max = max;
        this.min = min;
    }

    @Override
    public String getType() {
        return type;
    }
}

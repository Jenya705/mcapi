package com.github.jenya705.mcapi.command.types;

/**
 * @author Jenya705
 */
public class BooleanOption extends AbstractValueOption {

    public static final String type = "boolean";

    public BooleanOption(String name, boolean required, Object tab) {
        super(name, required, tab);
    }

    @Override
    public String getType() {
        return type;
    }
}

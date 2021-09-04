package com.github.jenya705.mcapi.command.types;

import lombok.Getter;

/**
 * @author Jenya705
 */
@Getter
public class StringOption extends AbstractValueOption {

    public static final String type = "string";

    public StringOption(String name, boolean required, Object tab) {
        super(name, required, tab);
    }

    @Override
    public String getType() {
        return type;
    }
}

package com.github.jenya705.mcapi.command.types;

import lombok.Getter;

/**
 * @author Jenya705
 */
@Getter
public class PlayerOption extends AbstractValueOption {

    public static final String type = "player";

    private final boolean onlyLinked;

    public PlayerOption(String name, boolean required, Object tab, boolean onlyFromTab, boolean onlyLinked) {
        super(name, required, tab, onlyFromTab);
        this.onlyLinked = onlyLinked;
    }

    @Override
    public String getType() {
        return type;
    }
}

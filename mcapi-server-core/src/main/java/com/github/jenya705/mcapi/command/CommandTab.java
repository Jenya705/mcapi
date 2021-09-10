package com.github.jenya705.mcapi.command;

/**
 * @author Jenya705
 */
public interface CommandTab {

    static CommandTab of(String name, String tooltip) {
        return new CommandTabImpl(name, tooltip);
    }

    static CommandTab of(String name) {
        return new CommandTabImpl(name, null);
    }

    String getName();

    String getTooltip();

}

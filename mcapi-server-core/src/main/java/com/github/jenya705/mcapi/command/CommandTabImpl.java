package com.github.jenya705.mcapi.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jenya705
 */
@Getter
@AllArgsConstructor
public class CommandTabImpl implements CommandTab {

    private final String name;
    private final String tooltip;

    public static CommandTabImpl of(String name) {
        return new CommandTabImpl(name, null);
    }

}

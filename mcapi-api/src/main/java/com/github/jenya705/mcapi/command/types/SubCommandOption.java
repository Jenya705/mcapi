package com.github.jenya705.mcapi.command.types;

import com.github.jenya705.mcapi.command.CommandExecutableOption;
import com.github.jenya705.mcapi.command.CommandValueOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jenya705
 */
@Getter
@AllArgsConstructor
public class SubCommandOption implements CommandExecutableOption {

    public static final String type = "sub_command";

    private final String name;
    private final CommandValueOption[] options;

    @Override
    public String getType() {
        return type;
    }
}

package com.github.jenya705.mcapi.command.types;

import com.github.jenya705.mcapi.command.CommandExecutableOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jenya705
 */
@Getter
@AllArgsConstructor
public class SubCommandGroupOption implements CommandExecutableOption {

    public static final String type = "sub_command_group";

    private final String name;
    private final CommandExecutableOption[] options;

    @Override
    public String getType() {
        return type;
    }
}

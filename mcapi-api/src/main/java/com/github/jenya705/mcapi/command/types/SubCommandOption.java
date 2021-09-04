package com.github.jenya705.mcapi.command.types;

import com.github.jenya705.mcapi.command.ApiCommandExecutableOption;
import com.github.jenya705.mcapi.command.ApiCommandValueOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jenya705
 */
@Getter
@AllArgsConstructor
public class SubCommandOption implements ApiCommandExecutableOption {

    public static final String type = "sub_command";

    private final String name;
    private final ApiCommandValueOption[] options;

    @Override
    public String getType() {
        return type;
    }
}

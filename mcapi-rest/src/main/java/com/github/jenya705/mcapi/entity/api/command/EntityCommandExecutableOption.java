package com.github.jenya705.mcapi.entity.api.command;

import com.github.jenya705.mcapi.command.CommandExecutableOption;
import com.github.jenya705.mcapi.command.CommandOption;
import com.github.jenya705.mcapi.entity.command.RestCommandExecutableOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityCommandExecutableOption implements CommandExecutableOption {

    private String name;
    private String type;
    private CommandOption[] options;

    public RestCommandExecutableOption rest() {
        return RestCommandExecutableOption.from(this);
    }
}

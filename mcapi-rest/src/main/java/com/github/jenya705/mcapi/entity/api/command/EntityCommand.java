package com.github.jenya705.mcapi.entity.api.command;

import com.github.jenya705.mcapi.command.Command;
import com.github.jenya705.mcapi.command.CommandOption;
import com.github.jenya705.mcapi.entity.command.RestCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityCommand implements Command {

    private String name;
    private CommandOption[] options;

    public RestCommand rest() {
        return RestCommand.from(this);
    }
}

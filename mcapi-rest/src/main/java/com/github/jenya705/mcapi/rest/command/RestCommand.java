package com.github.jenya705.mcapi.rest.command;

import com.github.jenya705.mcapi.command.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestCommand {

    private String name;
    private RestCommandOption[] options;

    public static RestCommand from(Command command) {
        return new RestCommand(
                command.getName(),
                Arrays
                        .stream(command.getOptions())
                        .map(RestCommandOption::from)
                        .toArray(RestCommandOption[]::new)
        );
    }
}

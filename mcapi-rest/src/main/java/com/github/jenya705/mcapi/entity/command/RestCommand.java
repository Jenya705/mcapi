package com.github.jenya705.mcapi.entity.command;

import com.github.jenya705.mcapi.command.ApiCommand;
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

    public static RestCommand from(ApiCommand command) {
        return new RestCommand(
                command.getName(),
                Arrays
                        .stream(command.getOptions())
                        .map(RestCommandOption::from)
                        .toArray(RestCommandOption[]::new)
        );
    }
}

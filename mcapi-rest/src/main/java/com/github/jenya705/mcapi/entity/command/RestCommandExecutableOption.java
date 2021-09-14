package com.github.jenya705.mcapi.entity.command;

import com.github.jenya705.mcapi.command.ApiCommandExecutableOption;
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
public class RestCommandExecutableOption {

    private String name;
    private RestCommandOption[] options;

    public static RestCommandExecutableOption from(ApiCommandExecutableOption option) {
        return new RestCommandExecutableOption(
                option.getName(),
                Arrays
                        .stream(option.getOptions())
                        .map(RestCommandOption::from)
                        .toArray(RestCommandOption[]::new)
        );
    }
}

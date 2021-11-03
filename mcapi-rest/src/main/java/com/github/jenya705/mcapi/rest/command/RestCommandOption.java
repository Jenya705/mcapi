package com.github.jenya705.mcapi.rest.command;

import com.github.jenya705.mcapi.command.CommandOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestCommandOption {

    private String name;
    private String type;

    public static RestCommandOption from(CommandOption option) {
        return new RestCommandOption(
                option.getName(),
                option.getType()
        );
    }
}

package com.github.jenya705.mcapi.rest.command;

import com.github.jenya705.mcapi.command.CommandInteractionValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestCommandInteractionValue {

    private String name;
    private Object value;

    public static RestCommandInteractionValue from(CommandInteractionValue value) {
        return new RestCommandInteractionValue(
                value.getName(),
                value.getValue()
        );
    }
}

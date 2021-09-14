package com.github.jenya705.mcapi.entity.command;

import com.github.jenya705.mcapi.command.ApiCommandInteractionValue;
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

    public static RestCommandInteractionValue from(ApiCommandInteractionValue value) {
        return new RestCommandInteractionValue(
                value.getName(),
                value.getValue()
        );
    }

}

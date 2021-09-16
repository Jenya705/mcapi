package com.github.jenya705.mcapi.entity.api.command;

import com.github.jenya705.mcapi.command.ApiCommandInteractionValue;
import com.github.jenya705.mcapi.entity.command.RestCommandInteractionValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityCommandInteractionValue implements ApiCommandInteractionValue {

    private String name;
    private Object value;

    public RestCommandInteractionValue rest() {
        return RestCommandInteractionValue.from(this);
    }

}

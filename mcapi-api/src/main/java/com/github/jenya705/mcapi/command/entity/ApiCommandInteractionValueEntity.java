package com.github.jenya705.mcapi.command.entity;

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
public class ApiCommandInteractionValueEntity implements ApiCommandInteractionValue {

    private String name;
    private Object value;

}

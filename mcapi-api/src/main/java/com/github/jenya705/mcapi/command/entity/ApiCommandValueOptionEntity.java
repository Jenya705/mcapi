package com.github.jenya705.mcapi.command.entity;

import com.github.jenya705.mcapi.command.ApiCommandValueOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiCommandValueOptionEntity implements ApiCommandValueOption {

    private String name;
    private String type;
    private boolean required;
    private String tabFunction;
    private String[] suggestions;

}

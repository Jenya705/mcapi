package com.github.jenya705.mcapi.command.entity;

import com.github.jenya705.mcapi.command.ApiCommandOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiCommandOptionEntity implements ApiCommandOption {

    private String name;
    private String type;

}

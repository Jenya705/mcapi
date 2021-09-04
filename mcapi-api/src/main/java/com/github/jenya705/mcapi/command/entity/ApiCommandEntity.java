package com.github.jenya705.mcapi.command.entity;

import com.github.jenya705.mcapi.command.ApiCommand;
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
public class ApiCommandEntity implements ApiCommand {

    private String name;
    private ApiCommandOption[] options;

}

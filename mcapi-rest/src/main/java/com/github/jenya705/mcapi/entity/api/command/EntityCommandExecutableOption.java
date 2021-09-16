package com.github.jenya705.mcapi.entity.api.command;

import com.github.jenya705.mcapi.command.ApiCommandExecutableOption;
import com.github.jenya705.mcapi.command.ApiCommandOption;
import com.github.jenya705.mcapi.entity.command.RestCommandExecutableOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityCommandExecutableOption implements ApiCommandExecutableOption {

    private String name;
    private String type;
    private ApiCommandOption[] options;

    public RestCommandExecutableOption rest() {
        return RestCommandExecutableOption.from(this);
    }
}

package com.github.jenya705.mcapi.entity.api.command;

import com.github.jenya705.mcapi.command.ApiCommand;
import com.github.jenya705.mcapi.command.ApiCommandOption;
import com.github.jenya705.mcapi.entity.command.RestCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityCommand implements ApiCommand {

    private String name;
    private ApiCommandOption[] options;

    public RestCommand rest() {
        return RestCommand.from(this);
    }

}

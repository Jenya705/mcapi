package com.github.jenya705.mcapi.entity.command;

import com.github.jenya705.mcapi.command.CommandOption;
import com.github.jenya705.mcapi.rest.command.RestCommandOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityCommandOption implements CommandOption {

    private String name;
    private String type;

    public RestCommandOption rest() {
        return RestCommandOption.from(this);
    }
}

package com.github.jenya705.mcapi.entity.api.command;

import com.github.jenya705.mcapi.command.ApiCommandOption;
import com.github.jenya705.mcapi.entity.command.RestCommandOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityCommandOption implements ApiCommandOption {

    private String name;
    private String type;

    public RestCommandOption rest() {
        return RestCommandOption.from(this);
    }

}

package com.github.jenya705.mcapi.entity.api.command;

import com.github.jenya705.mcapi.command.CommandValueOption;
import com.github.jenya705.mcapi.entity.command.RestCommandValueOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityCommandValueOption implements CommandValueOption {

    private boolean required;
    private boolean onlyFromTab;
    private String[] suggestions;
    private String tabFunction;
    private String name;
    private String type;

    public RestCommandValueOption rest() {
        return RestCommandValueOption.from(this);
    }
}

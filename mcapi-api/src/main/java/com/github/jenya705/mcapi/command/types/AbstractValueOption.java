package com.github.jenya705.mcapi.command.types;

import com.github.jenya705.mcapi.command.CommandValueOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jenya705
 */
@Getter
@AllArgsConstructor
public abstract class AbstractValueOption implements CommandValueOption {

    private final String name;
    private final boolean required;
    private final Object tab;
    private final boolean onlyFromTab;

    @Override
    public String getTabFunction() {
        return tab instanceof String ? (String) tab : null;
    }

    @Override
    public String[] getSuggestions() {
        return tab instanceof String[] ? (String[]) tab : null;
    }
}

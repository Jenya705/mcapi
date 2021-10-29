package com.github.jenya705.mcapi.builder.command.option.value;

import com.github.jenya705.mcapi.builder.command.option.CommandValueOptionBuilder;
import com.github.jenya705.mcapi.command.types.PlayerOption;

/**
 * @author Jenya705
 */
public class PlayerOptionBuilder extends CommandValueOptionBuilder<PlayerOptionBuilder, PlayerOption> {

    private boolean onlyLinked;

    public PlayerOptionBuilder onlyLinked() {
        onlyLinked = true;
        return this;
    }

    @Override
    public PlayerOption build() {
        validate();
        return new PlayerOption(
                getName(),
                isRequired(),
                getTab(),
                isOnlyFromTab(),
                onlyLinked
        );
    }
}

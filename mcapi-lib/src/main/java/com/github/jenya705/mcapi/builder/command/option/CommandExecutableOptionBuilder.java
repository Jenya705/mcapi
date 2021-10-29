package com.github.jenya705.mcapi.builder.command.option;

import com.github.jenya705.mcapi.builder.Buildable;
import com.github.jenya705.mcapi.builder.command.option.sub.SubCommandGroupOptionBuilder;
import com.github.jenya705.mcapi.builder.command.option.sub.SubCommandOptionBuilder;
import com.github.jenya705.mcapi.command.CommandExecutableOption;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author Jenya705
 */
@Getter(AccessLevel.PROTECTED)
public abstract class CommandExecutableOptionBuilder
        <E extends CommandExecutableOptionBuilder<E, T>, T extends CommandExecutableOption> implements Buildable<T> {

    public SubCommandGroupOptionBuilder group() {
        return new SubCommandGroupOptionBuilder();
    }

    public SubCommandOptionBuilder command() {
        return new SubCommandOptionBuilder();
    }

    private String name;

    @SuppressWarnings("unchecked")
    public E name(String name) {
        this.name = name;
        return (E) this;
    }

}

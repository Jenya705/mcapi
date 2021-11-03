package com.github.jenya705.mcapi.builder.command;

import com.github.jenya705.mcapi.builder.Buildable;
import com.github.jenya705.mcapi.command.Command;
import com.github.jenya705.mcapi.command.CommandExecutableOption;
import com.github.jenya705.mcapi.command.CommandOption;
import com.github.jenya705.mcapi.command.CommandValueOption;
import com.github.jenya705.mcapi.entity.command.EntityCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jenya705
 */
public class CommandBuilder implements Buildable<Command> {

    private String name;
    private List<CommandOption> options = new ArrayList<>();

    public static CommandBuilder create() {
        return new CommandBuilder();
    }

    public CommandBuilder name(String name) {
        this.name = name;
        return this;
    }

    public CommandBuilder option(CommandOption option) {
        if (options == null) options = new ArrayList<>();
        options.add(option);
        return this;
    }

    public CommandBuilder options(List<CommandOption> options) {
        this.options = options;
        return this;
    }

    public CommandBuilder options(CommandOption... options) {
        this.options = new ArrayList<>();
        this.options.addAll(Arrays.asList(options));
        return this;
    }

    public CommandBuilder validate() {
        if (name == null) {
            throw new IllegalStateException("Command name is not set");
        }
        boolean anySubs = false;
        boolean anyValues = false;
        for (CommandOption option: options) {
            if (option instanceof CommandValueOption) anyValues = true;
            else if (option instanceof CommandExecutableOption) anySubs = true;
        }
        if (anySubs && anyValues) {
            throw new IllegalArgumentException("Find two types of command options");
        }
        return this;
    }

    @Override
    public Command build() {
        validate();
        return new EntityCommand(
                name,
                options.toArray(CommandOption[]::new)
        );
    }

}

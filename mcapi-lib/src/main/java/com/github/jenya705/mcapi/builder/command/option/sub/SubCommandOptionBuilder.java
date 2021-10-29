package com.github.jenya705.mcapi.builder.command.option.sub;

import com.github.jenya705.mcapi.builder.command.option.CommandExecutableOptionBuilder;
import com.github.jenya705.mcapi.command.CommandValueOption;
import com.github.jenya705.mcapi.command.types.SubCommandOption;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jenya705
 */
public class SubCommandOptionBuilder extends
        CommandExecutableOptionBuilder<SubCommandOptionBuilder, SubCommandOption> {

    private List<CommandValueOption> options = new ArrayList<>();

    public SubCommandOptionBuilder option(CommandValueOption option) {
        if (options == null) options = new ArrayList<>();
        options.add(option);
        return this;
    }

    public SubCommandOptionBuilder options(CommandValueOption... options) {
        this.options = new ArrayList<>(Arrays.asList(options));
        return this;
    }

    public SubCommandOptionBuilder options(List<CommandValueOption> options) {
        this.options = new ArrayList<>(options);
        return this;
    }

    @Override
    public SubCommandOption build() {
        return new SubCommandOption(
                getName(),
                options.toArray(CommandValueOption[]::new)
        );
    }
}

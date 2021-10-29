package com.github.jenya705.mcapi.builder.command.option.sub;

import com.github.jenya705.mcapi.builder.command.option.CommandExecutableOptionBuilder;
import com.github.jenya705.mcapi.command.types.SubCommandGroupOption;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jenya705
 */
public class SubCommandGroupOptionBuilder extends
        CommandExecutableOptionBuilder<SubCommandGroupOptionBuilder, SubCommandGroupOption> {

    private List<SubCommandGroupOption> options = new ArrayList<>();

    public SubCommandGroupOptionBuilder option(SubCommandGroupOption option) {
        if (options == null) options = new ArrayList<>();
        options.add(option);
        return this;
    }

    public SubCommandGroupOptionBuilder options(SubCommandGroupOption... options) {
        this.options = new ArrayList<>();
        this.options.addAll(Arrays.asList(options));
        return this;
    }

    public SubCommandGroupOptionBuilder options(List<SubCommandGroupOption> options) {
        this.options = new ArrayList<>(options);
        return this;
    }

    @Override
    public SubCommandGroupOption build() {
        return new SubCommandGroupOption(
                getName(),
                options.toArray(SubCommandGroupOption[]::new)
        );
    }
}

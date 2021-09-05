package com.github.jenya705.mcapi.module.command;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.command.ApiCommandInteractionValue;
import com.github.jenya705.mcapi.command.ApiCommandValueOption;
import com.github.jenya705.mcapi.command.CommandExecutor;
import com.github.jenya705.mcapi.command.NoConfig;
import com.github.jenya705.mcapi.command.entity.ApiCommandInteractionResponseEntity;
import com.github.jenya705.mcapi.command.entity.ApiCommandInteractionValueEntity;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.gateway.object.CommandInteractionResponseObject;
import com.github.jenya705.mcapi.stringful.StringfulDataValueFunction;
import com.github.jenya705.mcapi.stringful.StringfulIterator;
import com.github.jenya705.mcapi.stringful.StringfulListParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@NoConfig
public class ApiCommandExecutor implements CommandExecutor, BaseCommon {

    public static final String others = "__others__";

    private final CommandModule commandModule;
    private final StringfulListParser parser;
    private final AbstractBot owner;
    private final List<String> names = new ArrayList<>();
    private final List<Supplier<List<String>>> tabs;
    private final String path;

    public ApiCommandExecutor(String path, CommandModule commandModule, AbstractBot owner, ApiCommandValueOption... valueOptions) {
        this.path = path;
        this.owner = owner;
        this.commandModule = commandModule;
        int requiredStart = Integer.MAX_VALUE;
        for (int i = 0; i < valueOptions.length; ++i) {
            ApiCommandValueOption option = valueOptions[i];
            if (!option.isRequired()) requiredStart = Math.min(i, requiredStart);
            names.add(option.getName());
        }
        parser = new StringfulListParser(
                requiredStart,
                Arrays.stream(valueOptions)
                        .map(this::generateStringfulFunction)
                        .collect(Collectors.toList())
        );
        tabs = Arrays.stream(valueOptions)
                .map(this::generateTabFunction)
                .collect(Collectors.toList());
    }

    @Override
    public void onCommand(ApiCommandSender sender, StringfulIterator args, String permission) {
        parser.create(args)
                .ifPresent(list ->
                        app()
                                .getGateway()
                                .broadcast(CommandInteractionResponseObject.of(
                                        new ApiCommandInteractionResponseEntity(
                                                path,
                                                parseValues(list, names, args.allNext()),
                                                sender
                                        )
                                ))
                );
    }

    @Override
    public List<String> onTab(ApiCommandSender sender, StringfulIterator args, String permission) {
        int countNext = args.countNext();
        return countNext < tabs.size() ? tabs.get(countNext).get() : null;
    }

    @Override
    public void setConfig(ConfigData config) {
        /* NOTHING */
    }

    public static ApiCommandInteractionValue[] parseValues(List<Object> values, List<String> names, String[] allNext) {
        if (values.size() > names.size()) {
            throw new IllegalArgumentException("Size of values bigger than size of names");
        }
        ApiCommandInteractionValue[] interactionValues = new ApiCommandInteractionValue[values.size() + 1];
        for (int i = 0; i < interactionValues.length - 1; ++i) {
            interactionValues[i] =
                    new ApiCommandInteractionValueEntity(names.get(i), values.get(i));
        }
        interactionValues[interactionValues.length - 1] = new ApiCommandInteractionValueEntity(others, allNext);
        return interactionValues;
    }

    private StringfulDataValueFunction<List<Object>> generateStringfulFunction(ApiCommandValueOption option) {
        return (list, value) -> {
            CommandValueOptionParser parser = getParser(option);
            Object obj = parser.serialize(option, owner, value);
            if (option.isOnlyFromTab() && !parser.tabs(option, owner).contains(String.valueOf(obj))) {
                throw new IllegalArgumentException(String.format("Not in tab list %s", obj));
            }
            list.add(obj);
        };
    }

    private Supplier<List<String>> generateTabFunction(ApiCommandValueOption option) {
        return () -> getParser(option).tabs(option, owner);
    }

    private CommandValueOptionParser getParser(ApiCommandValueOption option) {
        return commandModule.getParser(option.getType());
    }
}

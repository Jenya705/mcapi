package com.github.jenya705.mcapi.module.command;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.command.ApiCommandInteractionValue;
import com.github.jenya705.mcapi.command.ApiCommandValueOption;
import com.github.jenya705.mcapi.command.CommandExecutor;
import com.github.jenya705.mcapi.command.NoConfig;
import com.github.jenya705.mcapi.command.entity.ApiCommandInteractionResponseEntity;
import com.github.jenya705.mcapi.command.entity.ApiCommandInteractionValueEntity;
import com.github.jenya705.mcapi.command.types.BooleanOption;
import com.github.jenya705.mcapi.command.types.IntegerOption;
import com.github.jenya705.mcapi.command.types.StringOption;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.gateway.object.CommandInteractionResponseObject;
import com.github.jenya705.mcapi.stringful.StringfulDataValueFunction;
import com.github.jenya705.mcapi.stringful.StringfulIterator;
import com.github.jenya705.mcapi.stringful.StringfulListParser;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@NoConfig
public class ApiCommandExecutor implements CommandExecutor, BaseCommon {

    private static final Map<String, Function<ApiCommandValueOption, StringfulDataValueFunction<List<Object>>>> dataValues = new HashMap<>();

    static {
        dataValues.put(StringOption.type, option -> List::add);
        dataValues.put(IntegerOption.type, option -> (list, value) -> {
            int realValue = Integer.parseInt(value);
            IntegerOption realOption = (IntegerOption) option;
            if (realOption.getMin() > realValue && realOption.getMax() < realValue) {
                throw new IllegalArgumentException("Integer is not in range");
            }
            list.add(realValue);
        });
        dataValues.put(BooleanOption.type, option -> (list, value) -> list.add(Boolean.parseBoolean(value)));
    }

    private final StringfulListParser parser;
    private final List<String> names = new ArrayList<>();
    private final String path;

    public ApiCommandExecutor(String path, ApiCommandValueOption... valueOptions) {
        int requiredStart = Integer.MAX_VALUE;
        for (int i = 0; i < valueOptions.length; ++i) {
            ApiCommandValueOption option = valueOptions[i];
            if (!option.isRequired()) requiredStart = Math.min(i, requiredStart);
            names.add(option.getName());
        }
        parser = new StringfulListParser(
                requiredStart,
                Arrays.stream(valueOptions)
                        .map(it -> dataValues.get(it.getType()).apply(it))
                        .collect(Collectors.toList())
        );
        this.path = path;
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
                                                parseValues(list, names),
                                                sender
                                        )
                                ))
                );
    }

    @Override
    public List<String> onTab(ApiCommandSender sender, StringfulIterator args, String permission) {
        return null;
    }

    @Override
    public void setConfig(ConfigData config) {
        /* NOTHING */
    }

    public static ApiCommandInteractionValue[] parseValues(List<Object> values, List<String> names) {
        if (values.size() > names.size()) {
            throw new IllegalArgumentException("Size of values bigger than size of names");
        }
        ApiCommandInteractionValue[] interactionValues = new ApiCommandInteractionValue[values.size()];
        for (int i = 0; i < interactionValues.length; ++i) {
            interactionValues[i] =
                    new ApiCommandInteractionValueEntity(names.get(i), values.get(i));
        }
        return interactionValues;
    }
}

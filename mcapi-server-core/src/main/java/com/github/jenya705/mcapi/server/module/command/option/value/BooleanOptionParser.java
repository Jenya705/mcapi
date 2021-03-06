package com.github.jenya705.mcapi.server.module.command.option.value;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.jenya705.mcapi.command.CommandValueOption;
import com.github.jenya705.mcapi.command.types.BooleanOption;
import com.github.jenya705.mcapi.server.entity.AbstractBot;

import java.util.Arrays;

/**
 * @author Jenya705
 */
public class BooleanOptionParser extends AbstractCommandValueOptionParser {

    public BooleanOptionParser() {
        this
                .tab(tabDefault, (option, bot) -> Arrays.asList("true", "false"));
    }

    @Override
    public CommandValueOption valueDeserialize(JsonNode node) {
        return defaultDeserialize(BooleanOption::new, node);
    }

    @Override
    public Object serialize(CommandValueOption option, AbstractBot owner, String value) {
        return Boolean.parseBoolean(value);
    }
}

package com.github.jenya705.mcapi.server.module.command.option.value;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.jenya705.mcapi.command.CommandValueOption;
import com.github.jenya705.mcapi.command.types.StringOption;
import com.github.jenya705.mcapi.server.entity.AbstractBot;

/**
 * @author Jenya705
 */
public class StringOptionParser extends AbstractCommandValueOptionParser {

    @Override
    public CommandValueOption valueDeserialize(JsonNode node) {
        return defaultDeserialize(StringOption::new, node);
    }

    @Override
    public Object serialize(CommandValueOption option, AbstractBot owner, String value) {
        return value;
    }
}

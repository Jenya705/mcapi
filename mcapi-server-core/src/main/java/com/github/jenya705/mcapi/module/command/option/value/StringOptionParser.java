package com.github.jenya705.mcapi.module.command.option.value;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.jenya705.mcapi.command.ApiCommandValueOption;
import com.github.jenya705.mcapi.command.types.StringOption;
import com.github.jenya705.mcapi.entity.AbstractBot;

/**
 * @author Jenya705
 */
public class StringOptionParser extends AbstractCommandValueOptionParser {

    @Override
    public ApiCommandValueOption valueDeserialize(JsonNode node) {
        return defaultDeserialize(StringOption::new, node);
    }

    @Override
    public Object serialize(ApiCommandValueOption option, AbstractBot owner, String value) {
        return value;
    }
}

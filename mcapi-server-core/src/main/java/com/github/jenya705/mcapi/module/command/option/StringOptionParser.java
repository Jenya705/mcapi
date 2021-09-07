package com.github.jenya705.mcapi.module.command.option;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.jenya705.mcapi.command.ApiCommandValueOption;
import com.github.jenya705.mcapi.command.types.StringOption;
import com.github.jenya705.mcapi.entity.AbstractBot;

import java.util.List;

/**
 * @author Jenya705
 */
public class StringOptionParser extends AbstractCommandValueOptionParser {

    @Override
    public ApiCommandValueOption deserialize(JsonNode node) {
        return defaultDeserialize(StringOption::new, node);
    }

    @Override
    public Object serialize(ApiCommandValueOption option, AbstractBot owner, String value) {
        return value;
    }

}
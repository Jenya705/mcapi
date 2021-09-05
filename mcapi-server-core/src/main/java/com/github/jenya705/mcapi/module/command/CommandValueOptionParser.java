package com.github.jenya705.mcapi.module.command;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.jenya705.mcapi.command.ApiCommandValueOption;
import com.github.jenya705.mcapi.entity.AbstractBot;

import java.util.List;

/**
 * @author Jenya705
 */
public interface CommandValueOptionParser {

    ApiCommandValueOption deserialize(JsonNode node);

    Object serialize(ApiCommandValueOption option, AbstractBot owner, String value);

    List<String> tabs(ApiCommandValueOption option, AbstractBot owner);

}

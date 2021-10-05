package com.github.jenya705.mcapi.module.command;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.jenya705.mcapi.command.ApiCommandOption;
import com.github.jenya705.mcapi.command.ApiCommandValueOption;
import com.github.jenya705.mcapi.entity.AbstractBot;

import java.util.List;

/**
 * @author Jenya705
 */
public interface CommandOptionParser {

    ApiCommandOption deserialize(JsonNode node);

    Object serialize(ApiCommandOption option, AbstractBot owner, String value);

    List<String> tabs(ApiCommandOption option, AbstractBot owner);
}

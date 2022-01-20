package com.github.jenya705.mcapi.server.module.command;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.jenya705.mcapi.command.CommandOption;
import com.github.jenya705.mcapi.server.entity.AbstractBot;

import java.util.List;

/**
 * @author Jenya705
 */
public interface CommandOptionParser {

    CommandOption deserialize(JsonNode node);

    Object serialize(CommandOption option, AbstractBot owner, String value);

    void write(CommandOption option, JsonGenerator generator);

    List<String> tabs(CommandOption option, AbstractBot owner);
}

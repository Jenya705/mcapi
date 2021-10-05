package com.github.jenya705.mcapi.module.command.option.executable;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.jenya705.mcapi.command.ApiCommandExecutableOption;
import com.github.jenya705.mcapi.command.ApiCommandOption;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.module.command.CommandOptionParser;

import java.util.List;

/**
 * @author Jenya705
 */
public abstract class AbstractCommandExecutableOptionParser implements CommandOptionParser {

    @Override
    public final ApiCommandOption deserialize(JsonNode node) {
        return executableDeserialize(node);
    }

    @Override
    public final Object serialize(ApiCommandOption option, AbstractBot owner, String value) {
        return serialize((ApiCommandExecutableOption) option, owner, value);
    }

    @Override
    public List<String> tabs(ApiCommandOption option, AbstractBot owner) {
        return null;
    }

    public abstract ApiCommandExecutableOption executableDeserialize(JsonNode node);

    public Object serialize(ApiCommandExecutableOption option, AbstractBot owner, String value) {
        return null;
    }

}

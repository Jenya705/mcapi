package com.github.jenya705.mcapi.server.module.command.option.executable;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.jenya705.mcapi.command.CommandExecutableOption;
import com.github.jenya705.mcapi.command.CommandOption;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.module.command.CommandOptionParser;

import java.util.List;

/**
 * @author Jenya705
 */
public abstract class AbstractCommandExecutableOptionParser implements CommandOptionParser {

    @Override
    public final CommandOption deserialize(JsonNode node) {
        return executableDeserialize(node);
    }

    @Override
    public final Object serialize(CommandOption option, AbstractBot owner, String value) {
        return serialize((CommandExecutableOption) option, owner, value);
    }

    @Override
    public List<String> tabs(CommandOption option, AbstractBot owner) {
        return null;
    }

    public abstract CommandExecutableOption executableDeserialize(JsonNode node);

    public Object serialize(CommandExecutableOption option, AbstractBot owner, String value) {
        return null;
    }

}

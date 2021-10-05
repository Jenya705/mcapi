package com.github.jenya705.mcapi.module.command;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.jenya705.mcapi.command.ApiCommand;
import com.github.jenya705.mcapi.command.ApiCommandOption;
import com.github.jenya705.mcapi.entity.api.command.EntityCommand;
import com.github.jenya705.mcapi.entity.api.command.EntityCommandOption;
import com.github.jenya705.mcapi.util.IteratorUtils;

import java.io.IOException;
import java.util.function.IntFunction;

/**
 * @author Jenya705
 */
public class ApiCommandDeserializer extends StdDeserializer<ApiCommand> {

    private ApiCommandOption[] getSubOptions(JsonNode node) {
        return IteratorUtils.stream(
                node
                        .get("options")
                        .elements()
        )
                .map(it -> commandModule.getParser(it.get("type").asText()).deserialize(it))
                .toArray(ApiCommandOption[]::new);
    }

    private final CommandModule commandModule;

    public ApiCommandDeserializer(CommandModule commandModule) {
        this(ApiCommand.class, commandModule);
    }

    public ApiCommandDeserializer(Class<?> vc, CommandModule commandModule) {
        super(vc);
        this.commandModule = commandModule;
    }

    @Override
    public ApiCommand deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        return new EntityCommand(
                node.get("name").asText(),
                getSubOptions(node)
        );
    }
}

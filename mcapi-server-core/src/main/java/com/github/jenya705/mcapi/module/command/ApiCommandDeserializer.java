package com.github.jenya705.mcapi.module.command;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.jenya705.mcapi.command.Command;
import com.github.jenya705.mcapi.command.CommandOption;
import com.github.jenya705.mcapi.entity.command.EntityCommand;
import com.github.jenya705.mcapi.util.IteratorUtils;
import com.github.jenya705.mcapi.util.ObjectUtils;

import java.io.IOException;

/**
 * @author Jenya705
 */
public class ApiCommandDeserializer extends StdDeserializer<Command> {

    private CommandOption[] getSubOptions(JsonNode node) {
        return ObjectUtils.ifNotNullProcessOrElse(
                node.get("options"),
                options -> IteratorUtils
                        .stream(options.elements())
                        .map(it -> commandModule.getParser(it.get("type").asText()).deserialize(it))
                        .toArray(CommandOption[]::new),
                new CommandOption[0]
        );
    }

    private final CommandModule commandModule;

    public ApiCommandDeserializer(CommandModule commandModule) {
        this(Command.class, commandModule);
    }

    public ApiCommandDeserializer(Class<?> vc, CommandModule commandModule) {
        super(vc);
        this.commandModule = commandModule;
    }

    @Override
    public Command deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        return new EntityCommand(
                node.get("name").asText(),
                getSubOptions(node)
        );
    }
}

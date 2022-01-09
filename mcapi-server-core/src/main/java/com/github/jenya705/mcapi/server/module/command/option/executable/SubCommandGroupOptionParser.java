package com.github.jenya705.mcapi.server.module.command.option.executable;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.jenya705.mcapi.command.CommandExecutableOption;
import com.github.jenya705.mcapi.command.types.SubCommandGroupOption;
import com.github.jenya705.mcapi.error.BadOptionException;
import com.github.jenya705.mcapi.server.BaseCommon;
import com.github.jenya705.mcapi.server.ServerApplication;
import com.github.jenya705.mcapi.server.module.command.CommandModule;
import com.github.jenya705.mcapi.server.util.IteratorUtils;

/**
 * @author Jenya705
 */
public class SubCommandGroupOptionParser extends AbstractCommandExecutableOptionParser implements BaseCommon {

    private final ServerApplication application;

    private final CommandModule commandModule;

    @Override
    public ServerApplication app() {
        return application;
    }

    public SubCommandGroupOptionParser(ServerApplication application) {
        this.application = application;
        commandModule = bean(CommandModule.class);
    }

    @Override
    public CommandExecutableOption executableDeserialize(JsonNode node) {
        return new SubCommandGroupOption(
                node.get("name").asText(),
                IteratorUtils.stream(
                        node
                                .get("options")
                                .elements()
                )
                        .map(it -> commandModule.getParser(it.get("type").asText()).deserialize(it))
                        .map(it -> {
                            if (it instanceof CommandExecutableOption) return (CommandExecutableOption) it;
                            throw BadOptionException.create(it.getName());
                        })
                        .toArray(CommandExecutableOption[]::new)
        );
    }
}

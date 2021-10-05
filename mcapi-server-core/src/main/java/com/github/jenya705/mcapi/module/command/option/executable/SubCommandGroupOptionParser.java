package com.github.jenya705.mcapi.module.command.option.executable;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.command.ApiCommandExecutableOption;
import com.github.jenya705.mcapi.command.ApiCommandValueOption;
import com.github.jenya705.mcapi.command.types.SubCommandGroupOption;
import com.github.jenya705.mcapi.command.types.SubCommandOption;
import com.github.jenya705.mcapi.error.BadOptionException;
import com.github.jenya705.mcapi.module.command.CommandModule;
import com.github.jenya705.mcapi.util.IteratorUtils;

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
    public ApiCommandExecutableOption executableDeserialize(JsonNode node) {
        return new SubCommandGroupOption(
                node.get("name").asText(),
                IteratorUtils.stream(
                        node
                                .get("options")
                                .elements()
                )
                        .map(it -> commandModule.getParser(it.get("type").asText()).deserialize(it))
                        .map(it -> {
                            if (it instanceof ApiCommandExecutableOption) return (ApiCommandExecutableOption) it;
                            throw new BadOptionException(it.getName());
                        })
                        .toArray(ApiCommandExecutableOption[]::new)
        );
    }
}

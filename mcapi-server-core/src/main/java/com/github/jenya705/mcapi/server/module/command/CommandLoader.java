package com.github.jenya705.mcapi.server.module.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.jenya705.mcapi.command.Command;
import com.github.jenya705.mcapi.server.ServerCore;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.module.mapper.JacksonProvider;
import com.github.jenya705.mcapi.server.module.mapper.Mapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.*;

/**
 * @author Jenya705
 */
@Singleton
public class CommandLoader {

    public static final String fileNameFormat = "commands/%s.json";

    private final Mapper mapper;
    private final JacksonProvider jacksonProvider;
    private final ServerCore core;
    private final Logger log;

    @Inject
    public CommandLoader(Mapper mapper, JacksonProvider jacksonProvider, ServerCore core, Logger log) {
        this.mapper = mapper;
        this.jacksonProvider = jacksonProvider;
        this.core = core;
        this.log = log;
    }

    public void save(Command command, AbstractBot owner) throws IOException {
        List<Command> loadedCommands = loadBotCommands(owner.getEntity().getId());
        int index = 0;
        for (Command loadedCommand: loadedCommands) {
            if (loadedCommand.getName().equalsIgnoreCase(command.getName())) {
                break;
            }
            ++index;
        }
        if (index != loadedCommands.size()) {
            loadedCommands.set(index, command);
        }
        else {
            loadedCommands.add(command);
        }
        core.saveSpecific(
                String.format(fileNameFormat, owner.getEntity().getId()),
                mapper.asJson(loadedCommands).getBytes()
        );
    }

    public List<Command> loadBotCommands(int botId) throws IOException {
        String loadedJson = new String(core.loadSpecific(String.format(fileNameFormat, botId)));
        if (loadedJson.isEmpty()) {
            return new ArrayList<>();
        }
        ArrayNode objectNode = mapper.fromJson(loadedJson, ArrayNode.class);
        List<Command> result = new ArrayList<>();
        objectNode.elements().forEachRemaining(it ->
                result.add(jacksonProvider.getMapper()
                        .convertValue(it, Command.class)
                )
        );
        return result;
    }

    public Map<Integer, List<Command>> loadAllCommands() {
        Collection<String> files = core.getFilesInDirectory("commands");
        Map<Integer, List<Command>> result = new HashMap<>();
        for (String fileName: files) {
            try {
                int botId = Integer.parseInt(fileName.split("\\.", 2)[0]);
                result.put(botId, loadBotCommands(botId));
            } catch (Exception e) {
                log.error(String.format("Can not load bot commands file %s", fileName), e);
            }
        }
        return result;
    }

}

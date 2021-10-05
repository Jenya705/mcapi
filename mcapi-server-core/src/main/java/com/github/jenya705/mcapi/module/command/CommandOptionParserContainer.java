package com.github.jenya705.mcapi.module.command;

import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.command.types.*;
import com.github.jenya705.mcapi.module.command.option.executable.SubCommandGroupOptionParser;
import com.github.jenya705.mcapi.module.command.option.executable.SubCommandOptionParser;
import com.github.jenya705.mcapi.module.command.option.value.BooleanOptionParser;
import com.github.jenya705.mcapi.module.command.option.value.IntegerOptionParser;
import com.github.jenya705.mcapi.module.command.option.value.PlayerOptionParser;
import com.github.jenya705.mcapi.module.command.option.value.StringOptionParser;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jenya705
 */
public class CommandOptionParserContainer {

    private final Map<String, CommandOptionParser> parsers;

    public CommandOptionParserContainer(ServerApplication application) {
        parsers = new HashMap<>();
        parsers.put(BooleanOption.type, new BooleanOptionParser());
        parsers.put(IntegerOption.type, new IntegerOptionParser());
        parsers.put(StringOption.type, new StringOptionParser());
        parsers.put(PlayerOption.type, new PlayerOptionParser(application));

        addParser(SubCommandOption.type, new SubCommandOptionParser(application));
        addParser(SubCommandGroupOption.type, new SubCommandGroupOptionParser(application));
    }

    public CommandOptionParser getParser(String type) {
        return parsers.get(type);
    }

    public void addParser(String type, CommandOptionParser parser) {
        parsers.put(type, parser);
    }
}

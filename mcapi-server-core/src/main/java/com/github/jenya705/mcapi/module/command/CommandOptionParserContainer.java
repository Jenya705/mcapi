package com.github.jenya705.mcapi.module.command;

import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.command.types.BooleanOption;
import com.github.jenya705.mcapi.command.types.IntegerOption;
import com.github.jenya705.mcapi.command.types.PlayerOption;
import com.github.jenya705.mcapi.command.types.StringOption;
import com.github.jenya705.mcapi.module.command.option.BooleanOptionParser;
import com.github.jenya705.mcapi.module.command.option.IntegerOptionParser;
import com.github.jenya705.mcapi.module.command.option.PlayerOptionParser;
import com.github.jenya705.mcapi.module.command.option.StringOptionParser;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jenya705
 */
public class CommandOptionParserContainer {

    private final Map<String, CommandValueOptionParser> parsers;

    public CommandOptionParserContainer(ServerApplication application) {
        parsers = new HashMap<>();
        parsers.put(BooleanOption.type, new BooleanOptionParser());
        parsers.put(IntegerOption.type, new IntegerOptionParser());
        parsers.put(StringOption.type, new StringOptionParser());
        parsers.put(PlayerOption.type, new PlayerOptionParser(application));
    }

    public CommandValueOptionParser getParser(String type) {
        return parsers.get(type);
    }

    public void addParser(String type, CommandValueOptionParser parser) {
        parsers.put(type, parser);
    }
}

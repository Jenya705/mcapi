package com.github.jenya705.mcapi.server.module.command;

import com.github.jenya705.mcapi.command.types.*;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.module.command.option.executable.SubCommandGroupOptionParser;
import com.github.jenya705.mcapi.server.module.command.option.executable.SubCommandOptionParser;
import com.github.jenya705.mcapi.server.module.command.option.value.BooleanOptionParser;
import com.github.jenya705.mcapi.server.module.command.option.value.IntegerOptionParser;
import com.github.jenya705.mcapi.server.module.command.option.value.PlayerOptionParser;
import com.github.jenya705.mcapi.server.module.command.option.value.StringOptionParser;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jenya705
 */
public class CommandOptionParserContainer {

    private final Map<String, CommandOptionParser> parsers;

    public CommandOptionParserContainer(ServerApplication application) {
        parsers = new HashMap<>();
        // values
        addParser(BooleanOption.type, new BooleanOptionParser());
        addParser(IntegerOption.type, new IntegerOptionParser());
        addParser(StringOption.type, new StringOptionParser());
        addParser(PlayerOption.type, new PlayerOptionParser(application));
        // executables
        addParser(SubCommandOption.type, new SubCommandOptionParser(application));
        addParser(SubCommandGroupOption.type, new SubCommandGroupOptionParser(application));
    }

    public CommandOptionParser getParser(String type) {
        return parsers.get(type.toLowerCase(Locale.ROOT));
    }

    public void addParser(String type, CommandOptionParser parser) {
        parsers.put(type.toLowerCase(Locale.ROOT), parser);
    }
}

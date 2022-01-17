package com.github.jenya705.mcapi.server.module.command;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.command.CommandInteractionValue;
import com.github.jenya705.mcapi.command.CommandValueOption;
import com.github.jenya705.mcapi.entity.command.EntityCommandInteractionValue;
import com.github.jenya705.mcapi.entity.event.EntityCommandInteractionEvent;
import com.github.jenya705.mcapi.rest.command.RestCommandInteractionEvent;
import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.command.CommandExecutor;
import com.github.jenya705.mcapi.server.command.CommandTab;
import com.github.jenya705.mcapi.server.command.NoConfig;
import com.github.jenya705.mcapi.server.command.advanced.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.server.command.advanced.AdvancedCommandExecutorConfig;
import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.stringful.StringfulDataValueFunction;
import com.github.jenya705.mcapi.server.stringful.StringfulIterator;
import com.github.jenya705.mcapi.server.stringful.StringfulListParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@NoConfig
public class ApiCommandExecutor extends AbstractApplicationModule implements CommandExecutor {

    public static final String others = "__others__";

    private final CommandModule commandModule;
    private final StringfulListParser parser;
    private final AbstractBot owner;
    private final List<String> names = new ArrayList<>();
    private final List<Supplier<List<String>>> tabs;
    private final String path;

    private final AdvancedCommandExecutorConfig config;

    public ApiCommandExecutor(ServerApplication application, String path, CommandModule commandModule, AbstractBot owner, AdvancedCommandExecutorConfig config, CommandValueOption... valueOptions) {
        super(application);
        this.config = config;
        this.path = path;
        this.owner = owner;
        this.commandModule = commandModule;
        if (valueOptions.length == 0) {
            parser = null;
            tabs = null;
            return;
        }
        int requiredStart = Integer.MAX_VALUE;
        for (int i = 0; i < valueOptions.length; ++i) {
            CommandValueOption option = valueOptions[i];
            if (!option.isRequired()) requiredStart = Math.min(i, requiredStart);
            names.add(option.getName());
        }
        parser = new StringfulListParser(
                requiredStart,
                Arrays.stream(valueOptions)
                        .map(this::generateStringfulFunction)
                        .collect(Collectors.toList())
        );
        tabs = Arrays.stream(valueOptions)
                .map(this::generateTabFunction)
                .collect(Collectors.toList());
    }

    @Override
    public void onCommand(CommandSender sender, StringfulIterator args, String permission) {
        if (parser == null || tabs == null) {
            eventTunnel()
                    .broadcast(
                            new EntityCommandInteractionEvent(
                                    path,
                                    sender,
                                    new CommandInteractionValue[]{
                                            new EntityCommandInteractionValue(
                                                    ApiCommandExecutor.others,
                                                    args.allNext()
                                            )
                                    }
                            ),
                            RestCommandInteractionEvent.type
                    );
            return;
        }
        parser.create(args)
                .ifPresent(list ->
                        eventTunnel()
                                .broadcast(
                                        new EntityCommandInteractionEvent(
                                                path,
                                                sender,
                                                parseValues(list, names, args.allNext())
                                        ),
                                        RestCommandInteractionEvent.type
                                )
                )
                .ifFailed(error -> AdvancedCommandExecutor.handleOnError(app(), error, sender, config));
    }

    @Override
    public List<CommandTab> onTab(CommandSender sender, StringfulIterator args, String permission) {
        int countNext = args.countNext();
        return countNext < tabs.size() ?
                tabs
                        .get(countNext)
                        .get()
                        .stream()
                        .map(CommandTab::of)
                        .collect(Collectors.toList()) :
                null;
    }

    @Override
    public void setConfig(ConfigData config) {
        /* NOTHING */
    }

    public static CommandInteractionValue[] parseValues(List<Object> values, List<String> names, String[] allNext) {
        if (values.size() > names.size()) {
            throw new IllegalArgumentException("Size of values bigger than size of names");
        }
        CommandInteractionValue[] interactionValues = new EntityCommandInteractionValue[values.size() + 1];
        for (int i = 0; i < interactionValues.length - 1; ++i) {
            interactionValues[i] =
                    new EntityCommandInteractionValue(names.get(i), values.get(i));
        }
        interactionValues[interactionValues.length - 1] = new EntityCommandInteractionValue(others, allNext);
        return interactionValues;
    }

    private StringfulDataValueFunction<List<Object>> generateStringfulFunction(CommandValueOption option) {
        return (list, value) -> {
            CommandOptionParser parser = getParser(option);
            Object obj = parser.serialize(option, owner, value);
            if (option.isOnlyFromTab() && !parser.tabs(option, owner).contains(String.valueOf(obj))) {
                throw new IllegalArgumentException(String.format("Not in tab list %s", obj));
            }
            list.add(obj);
        };
    }

    private Supplier<List<String>> generateTabFunction(CommandValueOption option) {
        return () -> getParser(option).tabs(option, owner);
    }

    private CommandOptionParser getParser(CommandValueOption option) {
        return commandModule.getParser(option.getType());
    }
}

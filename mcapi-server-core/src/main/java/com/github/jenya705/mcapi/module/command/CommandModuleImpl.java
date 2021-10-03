package com.github.jenya705.mcapi.module.command;

import com.github.jenya705.mcapi.AbstractApplicationModule;
import com.github.jenya705.mcapi.OnStartup;
import com.github.jenya705.mcapi.command.*;
import com.github.jenya705.mcapi.command.advanced.AdvancedCommandExecutorConfig;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.error.CommandNameFormatException;
import com.github.jenya705.mcapi.error.CommandOptionsAllException;
import com.github.jenya705.mcapi.log.TimerTask;
import com.github.jenya705.mcapi.module.config.ConfigModule;
import com.github.jenya705.mcapi.module.mapper.Mapper;
import com.github.jenya705.mcapi.stringful.ArrayStringfulIterator;
import com.github.jenya705.mcapi.stringful.StringfulIterator;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jenya705
 */
@Slf4j
public class CommandModuleImpl extends AbstractApplicationModule implements CommandModule {

    private static final Pattern commandNamePattern = Pattern.compile("[a-zA-Z0-9_]*");

    private CommandOptionParserContainer parserContainer;
    private CommandModuleConfig config;

    private final Map<Integer, ContainerCommandExecutor> botCommands = new HashMap<>();

    @OnStartup
    public void start() {
        parserContainer = new CommandOptionParserContainer(app());
        TimerTask task = TimerTask.start(log, "Registering root command...");
        core().addCommand(RootCommand.name, new RootCommand(app()).get(), RootCommand.permission);
        task.complete();
        ConfigModule configModule = bean(ConfigModule.class);
        config = new CommandModuleConfig(
                configModule
                        .getConfig()
                        .required("customCommands")
        );
        bean(Mapper.class)
                .jsonDeserializer(ApiCommand.class, new ApiCommandDeserializer(this));
    }

    @Override
    public void registerCommand(ApiCommand command, AbstractBot owner) {
        validateCommandName(command.getName());
        Object endObject = parseOptions(command.getOptions(), command.getName(), owner);
        if (!(endObject instanceof Map) && !(endObject instanceof CommandExecutor)) {
            throw new IllegalArgumentException("CommandExecutor is null on the end");
        }
        ContainerCommandExecutor botCommand;
        if (botCommands.containsKey(owner.getEntity().getId())) {
            botCommand = botCommands.get(owner.getEntity().getId());
        }
        else {
            String globalCommandPermission =
                    String.format(permissionFormat, owner.getEntity().getId());
            botCommand = new ContainerCommandExecutor(
                    app(), owner.getEntity().getName(), globalCommandPermission
            );
            botCommand.withConfig(config.getContainer());
            core().addCommand(owner.getEntity().getName(), botCommand, globalCommandPermission);
            botCommands.put(owner.getEntity().getId(), botCommand);
        }
        botCommand
                .getNodes()
                .put(command.getName().toLowerCase(Locale.ROOT), endObject);
        botCommand.recalculatePermissions();
    }

    private Object parseOptions(ApiCommandOption[] options, String path, AbstractBot owner) {
        ValidateResult validateResult = validateOptions(options);
        if (validateResult == ValidateResult.ALL) {
            throw new CommandOptionsAllException();
        }
        if (validateResult == ValidateResult.SUBS) {
            Map<String, Object> newNode = new HashMap<>();
            for (ApiCommandOption option : options) {
                ApiCommandExecutableOption executableOption = (ApiCommandExecutableOption) option;
                newNode.put(
                        option.getName(),
                        parseOptions(
                                executableOption.getOptions(),
                                path + " " + option.getName(),
                                owner
                        )
                );
            }
            return newNode;
        }
        return new ApiCommandExecutor(
                app(),
                path,
                this,
                owner,
                config.getCommand(),
                Arrays.stream(options)
                        .map(it -> (ApiCommandValueOption) it)
                        .toArray(ApiCommandValueOption[]::new)
        );
    }

    enum ValidateResult {
        VALUES,
        SUBS,
        ALL
    }

    private ValidateResult validateOptions(ApiCommandOption[] options) {
        boolean anyValues = false;
        boolean anySubs = false;
        for (ApiCommandOption option : options) {
            validateCommandName(option.getName());
            if (anySubs && anyValues) break;
            if (option instanceof ApiCommandValueOption) anyValues = true;
            else if (option instanceof ApiCommandExecutableOption) anySubs = true;
        }
        return anyValues ? (anySubs ? ValidateResult.ALL : ValidateResult.VALUES) : ValidateResult.SUBS;
    }

    @Override
    public void addOptionParser(String type, CommandValueOptionParser parser) {
        parserContainer.addParser(type, parser);
    }

    @Override
    public CommandValueOptionParser getParser(String type) {
        return parserContainer.getParser(type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public CommandExecutor getBotCommandExecutor(AbstractBot bot, String command) {
        ContainerCommandExecutor botCommandExecutor = botCommands
                .getOrDefault(bot.getEntity().getId(), null);
        if (botCommandExecutor == null) return null;
        Map<String, Object> currentNode = botCommandExecutor.getNodes();
        StringfulIterator iterator = new ArrayStringfulIterator(command.split(" "));
        while (iterator.hasNext()) {
            String next = iterator.next();
            Object nodeObject = currentNode.getOrDefault(next, null);
            if (nodeObject == null) return null;
            if (nodeObject instanceof CommandExecutor) {
                if (iterator.hasNext()) return null;
                return (CommandExecutor) nodeObject;
            }
            if (nodeObject instanceof Map) {
                currentNode = (Map<String, Object>) nodeObject;
            }
            else {
                throw new IllegalStateException("Bad node");
            }
        }
        return null;
    }

    private static boolean isCommandNameRight(String commandName) {
        Matcher matcher = commandNamePattern.matcher(commandName);
        return matcher.find() && matcher.end() == commandName.length();
    }

    private static void validateCommandName(String commandName) {
        if (!isCommandNameRight(commandName)) {
            throw new CommandNameFormatException(commandNamePattern.pattern());
        }
    }
}

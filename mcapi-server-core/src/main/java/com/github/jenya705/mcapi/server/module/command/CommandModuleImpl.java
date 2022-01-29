package com.github.jenya705.mcapi.server.module.command;

import com.github.jenya705.mcapi.command.Command;
import com.github.jenya705.mcapi.command.CommandExecutableOption;
import com.github.jenya705.mcapi.command.CommandOption;
import com.github.jenya705.mcapi.command.CommandValueOption;
import com.github.jenya705.mcapi.error.BotCommandNotExistException;
import com.github.jenya705.mcapi.error.CommandNameFormatException;
import com.github.jenya705.mcapi.error.CommandOptionsAllException;
import com.github.jenya705.mcapi.error.TooManyOptionsException;
import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.command.CommandExecutor;
import com.github.jenya705.mcapi.server.command.ContainerCommandExecutor;
import com.github.jenya705.mcapi.server.command.RootCommand;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.log.TimerTask;
import com.github.jenya705.mcapi.server.module.authorization.AuthorizationModule;
import com.github.jenya705.mcapi.server.module.config.ConfigModule;
import com.github.jenya705.mcapi.server.module.config.message.MessageContainer;
import com.github.jenya705.mcapi.server.module.mapper.Mapper;
import com.github.jenya705.mcapi.server.stringful.ArrayStringfulIterator;
import com.github.jenya705.mcapi.server.stringful.StringfulIterator;
import com.github.jenya705.mcapi.server.util.PatternUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jenya705
 */
@Slf4j
@Singleton
public class CommandModuleImpl extends AbstractApplicationModule implements CommandModule {

    private final CommandOptionParserContainer parserContainer;
    private final CommandModuleConfig config;
    private final CommandLoader commandLoader;
    private final MessageContainer messageContainer;

    private final Map<Integer, ContainerCommandExecutor> botCommands = new ConcurrentHashMap<>();

    @Inject
    public CommandModuleImpl(ServerApplication application, ConfigModule configModule, Mapper mapper,
                             CommandLoader commandLoader, AuthorizationModule authorizationModule,
                             MessageContainer messageContainer) {
        super(application);
        this.commandLoader = commandLoader;
        this.messageContainer = messageContainer;
        parserContainer = new CommandOptionParserContainer(app());
        TimerTask task = TimerTask.start(log, "Registering root command...");
        core().addCommand(RootCommand.name, new RootCommand(app()).get(), RootCommand.permission);
        task.complete();
        config = new CommandModuleConfig(
                configModule
                        .getConfig()
                        .required("customCommands")
        );
        mapper
                .jsonDeserializer(Command.class, new ApiCommandDeserializer(this))
                .jsonSerializer(CommandOption.class, (value, generator, serializers) ->
                        parserContainer.getParser(value.getType()).write(value, generator)
                );
        commandLoader.loadAllCommands().forEach((id, commands) -> {
            AbstractBot owner = authorizationModule.botById(id);
            commands.forEach(command -> registerCommandWithoutCheck(command, owner, false));
        });
    }

    @Override
    public void registerCommand(Command command, AbstractBot owner) {
        validateCommandName(command.getName());
        registerCommandWithoutCheck(command, owner, true);
    }

    protected void registerCommandWithoutCheck(Command command, AbstractBot owner, boolean save) {
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
            core().addCommand(owner.getEntity().getName(), botCommand, globalCommandPermission);
            botCommands.put(owner.getEntity().getId(), botCommand);
        }
        if (save) {
            worker().exceptionable(
                    () -> commandLoader.save(command, owner),
                    e -> log.error("Failed to save command", e)
            );
        }
        botCommand.putUnsafe(command.getName(), endObject);
        botCommand.recalculatePermissions();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void deleteCommand(String name, AbstractBot owner) {
        if (!botCommands.containsKey(owner.getEntity().getId())) {
            throw BotCommandNotExistException.create(name);
        }
        ContainerCommandExecutor commandExecutor = botCommands.get(owner.getEntity().getId());
        String[] path = name.split("_");
        Map<String, Object> currentNode = commandExecutor.getNodes();
        for (String command: path) {
            Object obj = currentNode.getOrDefault(command, null);
            if (obj == null) {
                throw BotCommandNotExistException.create(name);
            }
            if (obj instanceof Map) {
                currentNode = (Map<String, Object>) obj;
            }
            else if (obj instanceof CommandExecutor) {
               currentNode.remove(command);
            }
        }
    }

    private Object parseOptions(CommandOption[] options, String path, AbstractBot owner) {
        ValidateResult validateResult = validateOptions(options);
        if (validateResult == ValidateResult.ALL) {
            throw CommandOptionsAllException.create();
        }
        if (validateResult == ValidateResult.SUBS) {
            Map<String, Object> newNode = new HashMap<>();
            for (CommandOption option : options) {
                CommandExecutableOption executableOption = (CommandExecutableOption) option;
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
                messageContainer,
                Arrays.stream(options)
                        .map(it -> (CommandValueOption) it)
                        .toArray(CommandValueOption[]::new)
        );
    }

    enum ValidateResult {
        VALUES,
        SUBS,
        ALL
    }

    private ValidateResult validateOptions(CommandOption[] options) {
        if (options.length > config.getMaxCommandOptions()) {
            throw TooManyOptionsException.create(config.getMaxCommandOptions());
        }
        boolean anyValues = false;
        boolean anySubs = false;
        for (CommandOption option : options) {
            validateCommandName(option.getName());
            if (anySubs && anyValues) break;
            if (option instanceof CommandValueOption) anyValues = true;
            else if (option instanceof CommandExecutableOption) anySubs = true;
        }
        return anySubs ? (anyValues ? ValidateResult.ALL : ValidateResult.SUBS) : ValidateResult.VALUES;
    }

    @Override
    public void addOptionParser(String type, CommandOptionParser parser) {
        parserContainer.addParser(type, parser);
    }

    @Override
    public CommandOptionParser getParser(String type) {
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

    private boolean isCommandNameRight(String commandName) {
        return PatternUtils.validateAllString(config.getCommandNamePattern(), commandName);
    }

    private void validateCommandName(String commandName) {
        if (!isCommandNameRight(commandName)) {
            throw CommandNameFormatException.create(config.getCommandNamePattern().pattern());
        }
    }
}

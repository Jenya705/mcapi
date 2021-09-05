package com.github.jenya705.mcapi.module.command;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.JacksonProvider;
import com.github.jenya705.mcapi.OnStartup;
import com.github.jenya705.mcapi.command.*;
import com.github.jenya705.mcapi.entity.BotEntity;
import com.github.jenya705.mcapi.error.CommandOptionsAllException;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jenya705
 */
@Slf4j
public class CommandModuleImpl implements CommandModule, BaseCommon {

    private static final String permissionFormat = "mcapi.bot.%s.%s";

    @OnStartup
    public void start() {
        log.info("Registering root command...");
        core().addCommand(RootCommand.name, new RootCommand().get(), RootCommand.permission);
        log.info("Done! (Registering root command...)");
        registerSerializers();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void registerCommand(ApiCommand command, BotEntity owner) {
        Object endObject = parseOptions(command.getOptions(), command.getName());
        CommandExecutor commandExecutor = null;
        String commandPermission = String.format(permissionFormat, owner.getId(), command.getName());
        if (endObject instanceof Map) {
            commandExecutor = new ContainerCommandExecutor(
                    (Map<String, Object>) endObject,
                    command.getName(),
                    commandPermission
            );
        }
        else if (endObject instanceof CommandExecutor) {
            commandExecutor = (CommandExecutor) endObject;
        }
        if (commandExecutor == null) {
            throw new IllegalArgumentException("CommandExecutor is null on the end");
        }
        core()
                .addCommand(
                        command.getName(),
                        commandExecutor,
                        commandPermission
                );
        core()
                .permission(commandPermission, false);
    }

    private Object parseOptions(ApiCommandOption[] options, String path) {
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
                                path + " " + option.getName()
                        )
                );
            }
            return newNode;
        }
        return new ApiCommandExecutor(
                path,
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
            if (option instanceof ApiCommandValueOption) anyValues = true;
            else if (option instanceof ApiCommandExecutableOption) anySubs = true;
        }
        return anyValues ? (anySubs ? ValidateResult.ALL : ValidateResult.VALUES) : ValidateResult.SUBS;
    }

    private void registerSerializers() {
        SimpleModule jacksonModule = new SimpleModule();
        jacksonModule.addDeserializer(ApiCommand.class, new ApiCommandDeserializer());
        JacksonProvider
                .getMapper()
                .registerModule(jacksonModule);
    }
}

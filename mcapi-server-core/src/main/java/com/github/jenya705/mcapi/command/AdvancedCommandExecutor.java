package com.github.jenya705.mcapi.command;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.stringful.StringfulIterator;
import com.github.jenya705.mcapi.stringful.StringfulParser;
import com.github.jenya705.mcapi.stringful.StringfulParserImpl;
import com.github.jenya705.mcapi.util.PlayerUtils;
import lombok.AccessLevel;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Jenya705
 */
public abstract class AdvancedCommandExecutor<T> implements CommandExecutor, BaseCommon {

    private final List<Supplier<List<String>>> tabs = new ArrayList<>();
    private final StringfulParser<T> parser;
    @Setter(AccessLevel.PROTECTED)
    private AdvancedCommandExecutorConfig config;

    public AdvancedCommandExecutor(Class<? extends T> argsClass) {
        try {
            parser = new StringfulParserImpl<>(argsClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onCommand(ApiCommandSender sender, StringfulIterator args, String permission) {
        parser.create(args)
                .ifPresent(data -> onCommand(sender, data, permission))
                .ifFailed(error -> {
                    if (error.isNotEnoughArguments()) {
                        sendMessage(sender, config.getNotEnoughArguments());
                    }
                    else {
                        sendMessage(sender, config.getArgumentParseFailed(),
                                "%argument_id%", Integer.toString(error.onArgument())
                        );
                    }
                });
    }

    public abstract void onCommand(ApiCommandSender sender, T args, String permission);

    public boolean hasPermission(ApiCommandSender sender, String rootPermission, String permission) {
        return sender.hasPermission(rootPermission + "." + permission);
    }

    public void sendMessage(ApiCommandSender sender, String message, String... placeholders) {
        sender.sendMessage(CommandsUtils.placeholderMessage(message, placeholders));
    }

    public <E> void sendListMessage(
            ApiCommandSender sender,
            String layout,
            String element,
            String delimiter,
            List<E> elements,
            Function<E, String[]> placeholdersGet,
            String... layoutPlaceholders
    ) {
        sender.sendMessage(CommandsUtils.listMessage(
                layout, element, delimiter, elements, placeholdersGet, layoutPlaceholders
        ));
    }

    public <E> void sendListMessage(
            ApiCommandSender sender,
            String layout,
            String element,
            String delimiter,
            List<E> elements,
            Function<E, String[]> placeholdersGet,
            int size,
            int page,
            String... layoutPlaceholders
    ) {
        sender.sendMessage(CommandsUtils.listMessage(
                layout, element, delimiter, elements, placeholdersGet, size, page, layoutPlaceholders
        ));
    }

    public Optional<ApiPlayer> getPlayer(ApiCommandSender sender, String name) {
        if (name == null) {
            return Optional
                    .of(sender)
                    .filter(it -> it instanceof ApiPlayer)
                    .map(it -> (ApiPlayer) it);
        }
        return PlayerUtils.getPlayerWithoutException(name, core());
    }

    @Override
    public List<String> onTab(ApiCommandSender sender, StringfulIterator args, String permission) {
        int count = args.countNext();
        if (count > tabs.size() || count == 0) return null;
        return tabs.get(count - 1).get();
    }

    public AdvancedCommandExecutor<T> tab(Supplier<List<String>> tabFunction) {
        tabs.add(tabFunction);
        return this;
    }
}

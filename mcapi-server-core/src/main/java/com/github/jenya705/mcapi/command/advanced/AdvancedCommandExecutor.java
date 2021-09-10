package com.github.jenya705.mcapi.command.advanced;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.command.CommandExecutor;
import com.github.jenya705.mcapi.command.CommandTab;
import com.github.jenya705.mcapi.command.CommandsUtils;
import com.github.jenya705.mcapi.module.database.DatabaseModule;
import com.github.jenya705.mcapi.stringful.StringfulIterator;
import com.github.jenya705.mcapi.stringful.StringfulParser;
import com.github.jenya705.mcapi.stringful.StringfulParserImpl;
import com.github.jenya705.mcapi.util.PlayerUtils;
import lombok.AccessLevel;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Jenya705
 */
public abstract class AdvancedCommandExecutor<T> implements CommandExecutor, BaseCommon {

    private final DatabaseModule databaseModule = bean(DatabaseModule.class);
    private final List<TabFunction> tabs = new ArrayList<>();
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
    public List<CommandTab> onTab(ApiCommandSender sender, StringfulIterator args, String permission) {
        return allTab(sender, args, permission, false);
    }

    @Override
    public List<CommandTab> asyncTab(ApiCommandSender sender, StringfulIterator args, String permission) {
        return allTab(sender, args, permission, true);
    }

    private List<CommandTab> allTab(ApiCommandSender sender, StringfulIterator args, String permission, boolean async) {
        int count = args.countNext();
        if (count > tabs.size() || count == 0) return null;
        return tabs.get(count - 1).apply(sender, permission, async);
    }

    public AdvancedCommandExecutor<T> tab(Supplier<List<String>> tabFunction) {
        tabs.add(DefaultTabFunction.syncOldFunction(tabFunction));
        return this;
    }

    public AdvancedCommandExecutor<T> tab(BiFunction<ApiCommandSender, String, List<String>> tabFunction) {
        tabs.add(DefaultTabFunction.syncOldFunction(tabFunction));
        return this;
    }

    public AdvancedCommandExecutor<T> tooltipTab(Supplier<List<CommandTab>> tabFunction) {
        tabs.add(DefaultTabFunction.syncFunction(tabFunction));
        return this;
    }

    public AdvancedCommandExecutor<T> tooltipTab(BiFunction<ApiCommandSender, String, List<CommandTab>> tabFunction) {
        tabs.add(DefaultTabFunction.syncFunction(tabFunction));
        return this;
    }

    public AdvancedCommandExecutor<T> tab(TabFunction tabFunction) {
        tabs.add(tabFunction);
        return this;
    }

    public AdvancedCommandExecutor<T> databaseTab(DatabaseTabFunction databaseTabFunction, boolean withFuture) {
        tabs.add(
                DefaultTabFunction.databaseFunction(
                        databaseTabFunction,
                        withFuture ?
                                databaseModule.safeSyncWithFuture() :
                                databaseModule.safeSync(),
                        databaseModule.safeAsync()
                )
        );
        return this;
    }
}

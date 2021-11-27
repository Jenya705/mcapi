package com.github.jenya705.mcapi.command.advanced;

import com.github.jenya705.mcapi.AbstractApplicationModule;
import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.command.CommandExecutor;
import com.github.jenya705.mcapi.command.CommandTab;
import com.github.jenya705.mcapi.command.CommandsUtils;
import com.github.jenya705.mcapi.module.database.DatabaseModule;
import com.github.jenya705.mcapi.module.mapper.Mapper;
import com.github.jenya705.mcapi.stringful.StringfulIterator;
import com.github.jenya705.mcapi.stringful.StringfulParseError;
import com.github.jenya705.mcapi.stringful.StringfulParser;
import com.github.jenya705.mcapi.stringful.StringfulParserImpl;
import com.github.jenya705.mcapi.util.PlayerUtils;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Jenya705
 */
@Slf4j
public abstract class AdvancedCommandExecutor<T> extends AbstractApplicationModule implements CommandExecutor {

    private final DatabaseModule databaseModule;
    private final List<TabFunction> tabs = new ArrayList<>();
    private final StringfulParser<T> parser;
    @Setter(AccessLevel.PROTECTED)
    private AdvancedCommandExecutorConfig config;

    public AdvancedCommandExecutor(ServerApplication application, Class<? extends T> argsClass) {
        super(application);
        databaseModule = bean(DatabaseModule.class);
        try {
            parser = new StringfulParserImpl<>(argsClass, bean(Mapper.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onCommand(CommandSender sender, StringfulIterator args, String permission) {
        parser.create(args)
                .ifPresent(data -> onCommand(sender, data, permission))
                .ifFailed(error -> handleOnError(app(), error, sender, config));
    }

    public abstract void onCommand(CommandSender sender, T args, String permission);

    public boolean hasPermission(CommandSender sender, String rootPermission, String permission) {
        return sender.hasPermission(rootPermission + "." + permission);
    }

    public void sendMessage(CommandSender sender, String message, String... placeholders) {
        sender.sendMessage(CommandsUtils.placeholderMessage(message, placeholders));
    }

    public <E> void sendListMessage(
            CommandSender sender,
            String layout,
            String element,
            String delimiter,
            Collection<E> elements,
            Function<E, String[]> placeholdersGet,
            String... layoutPlaceholders
    ) {
        sender.sendMessage(CommandsUtils.listMessage(
                layout, element, delimiter, elements, placeholdersGet, layoutPlaceholders
        ));
    }

    public <E> void sendListMessage(
            CommandSender sender,
            String layout,
            String element,
            String delimiter,
            Collection<E> elements,
            Function<E, String[]> placeholdersGet,
            int size,
            int page,
            String... layoutPlaceholders
    ) {
        sender.sendMessage(CommandsUtils.listMessage(
                layout, element, delimiter, elements, placeholdersGet, size, page, layoutPlaceholders
        ));
    }

    public Optional<? extends Player> getPlayer(CommandSender sender, String name) {
        if (name == null) {
            return Optional
                    .of(sender)
                    .filter(it -> it instanceof Player)
                    .map(it -> (Player) it);
        }
        return PlayerUtils.getPlayerWithoutException(name, core());
    }

    @Override
    public List<CommandTab> onTab(CommandSender sender, StringfulIterator args, String permission) {
        return allTab(sender, args, permission, false);
    }

    @Override
    public List<CommandTab> asyncTab(CommandSender sender, StringfulIterator args, String permission) {
        return allTab(sender, args, permission, true);
    }

    private List<CommandTab> allTab(CommandSender sender, StringfulIterator args, String permission, boolean async) {
        int count = args.countNext();
        if (count > tabs.size() || count == 0) return null;
        return tabs.get(count - 1).apply(sender, permission, async);
    }

    public AdvancedCommandExecutor<T> tab(Supplier<List<String>> tabFunction) {
        tabs.add(DefaultTabFunction.syncOldFunction(tabFunction));
        return this;
    }

    public AdvancedCommandExecutor<T> tab(BiFunction<CommandSender, String, List<String>> tabFunction) {
        tabs.add(DefaultTabFunction.syncOldFunction(tabFunction));
        return this;
    }

    public AdvancedCommandExecutor<T> tooltipTab(Supplier<List<CommandTab>> tabFunction) {
        tabs.add(DefaultTabFunction.syncFunction(tabFunction));
        return this;
    }

    public AdvancedCommandExecutor<T> tooltipTab(BiFunction<CommandSender, String, List<CommandTab>> tabFunction) {
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

    public static void handleOnError(ServerApplication application, StringfulParseError error, CommandSender sender, AdvancedCommandExecutorConfig config) {
        if (application.isDebug() && error.causedBy() != null) {
            log.warn("Exception during command execution: ", error.causedBy());
        }
        if (error.isNotEnoughArguments()) {
            sender.sendMessage(CommandsUtils.placeholderMessage(
                    config.getNotEnoughArguments()
            ));
        }
        else {
            sender.sendMessage(CommandsUtils.placeholderMessage(
                    config.getArgumentParseFailed(),
                    "%argument_id%", Integer.toString(error.onArgument())
            ));
        }
    }

}

package com.github.jenya705.mcapi.server.command.advanced;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.command.CommandExecutor;
import com.github.jenya705.mcapi.server.command.CommandTab;
import com.github.jenya705.mcapi.server.command.CommandsUtils;
import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.module.config.message.MessageContainer;
import com.github.jenya705.mcapi.server.module.database.DatabaseModule;
import com.github.jenya705.mcapi.server.module.mapper.Mapper;
import com.github.jenya705.mcapi.server.stringful.StringfulIterator;
import com.github.jenya705.mcapi.server.stringful.StringfulParseError;
import com.github.jenya705.mcapi.server.stringful.StringfulParser;
import com.github.jenya705.mcapi.server.stringful.StringfulParserImpl;
import com.github.jenya705.mcapi.server.util.PlayerUtils;
import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
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
    private final MessageContainer messageContainer;

    protected MessageContainer messageContainer() {
        return messageContainer;
    }

    public AdvancedCommandExecutor(ServerApplication application, MessageContainer messageContainer, Class<? extends T> argsClass) {
        super(application);
        databaseModule = bean(DatabaseModule.class);
        this.messageContainer = messageContainer;
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
                .ifFailed(error -> handleOnError(app(), error, sender, messageContainer));
    }

    public abstract void onCommand(CommandSender sender, T args, String permission);

    public boolean hasPermission(CommandSender sender, String rootPermission, String permission) {
        return sender.hasPermission(rootPermission + "." + permission);
    }

    public void sendMessage(CommandSender sender, Component message) {
        sender.sendMessage(messageContainer.render(message, sender));
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

    public void requirePlayer(CommandSender sender, String name, Consumer<Player> handler) {
        if (name == null) {
            if (sender instanceof Player) {
                handler.accept((Player) sender);
            }
            else {
                sendMessage(sender, messageContainer().onlyForPlayers());
            }
        }
        else {
            PlayerUtils
                    .getPlayerWithoutException(name, core())
                    .ifPresentOrElse(
                            handler,
                            () -> sendMessage(sender, messageContainer.playerNotFound(name))
                    );
        }
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

    @Override
    public void setConfig(ConfigData config) {

    }

    public static void handleOnError(ServerApplication application, StringfulParseError error, CommandSender sender, MessageContainer messageContainer) {
        if (application != null && application.isDebug() && error.causedBy() != null) {
            log.warn("Exception during command execution: ", error.causedBy());
        }
        if (error.isNotEnoughArguments()) {
            sender.sendMessage(messageContainer.render(
                    messageContainer.stringfulNotEnoughArguments(), sender
            ));
        }
        else {
            sender.sendMessage(messageContainer.render(
                    messageContainer.stringfulFailedToParse(error.onArgument()), sender
            ));
        }
    }

}

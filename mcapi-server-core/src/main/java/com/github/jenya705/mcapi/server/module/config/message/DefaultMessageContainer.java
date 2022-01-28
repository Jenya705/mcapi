package com.github.jenya705.mcapi.server.module.config.message;

import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.entity.BotLinkEntity;
import com.github.jenya705.mcapi.server.entity.BotPermissionEntity;
import com.github.jenya705.mcapi.server.util.Pair;
import com.google.inject.Singleton;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import net.kyori.adventure.util.UTF8ResourceBundleControl;

import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@Singleton
public class DefaultMessageContainer implements MessageContainer {

    private static final TextColor defaultColor = NamedTextColor.YELLOW;
    private static final TextColor signColor = NamedTextColor.GRAY;
    private static final TextColor successColor = NamedTextColor.GREEN;
    private static final TextColor errorColor = NamedTextColor.RED;
    private static final TextColor neutralColor = NamedTextColor.BLUE;

    private static final Component dash = Component.text("-").color(signColor);

    private final TranslationRegistry registry;

    public DefaultMessageContainer() {
        registry = TranslationRegistry.create(Key.key("mcapi", "main"));
        registry.defaultLocale(Locale.ENGLISH);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("mcapi", Locale.ENGLISH, UTF8ResourceBundleControl.get());
        registry.registerAll(Locale.ENGLISH, resourceBundle, false);
        GlobalTranslator.get().addSource(registry);
    }

    @Override
    public Component commandHelp(Collection<? extends String> commands, String commandStart) {
        return Component.translatable("mcapi.command.help.header")
                .color(defaultColor)
                .append(list(
                        commands
                                .stream()
                                .map(command -> Component
                                        .text(command)
                                        .clickEvent(ClickEvent.clickEvent(
                                                ClickEvent.Action.SUGGEST_COMMAND,
                                                commandStart + command
                                        ))
                                )
                                .collect(Collectors.toList())
                ));
    }

    private static final Component notPermitted = Component
            .translatable("mcapi.notpermitted")
            .color(errorColor);

    @Override
    public Component notPermitted() {
        return notPermitted;
    }

    private static final Component success = Component
            .translatable("mcapi.success")
            .color(successColor);

    @Override
    public Component success() {
        return success;
    }

    private static final Component badSuccess = success.color(errorColor);

    @Override
    public Component badSuccess() {
        return badSuccess;
    }

    private static final Component warning = Component
            .translatable("mcapi.warning")
            .decorate(TextDecoration.BOLD)
            .color(errorColor);

    @Override
    public Component warning() {
        return warning;
    }

    @Override
    public Component botCreation(String token) {
        return success()
                .append(Component.space())
                .append(Component
                        .translatable("mcapi.bot.create")
                        .color(defaultColor)
                        .append(Component.space())
                        .append(Component
                                .text(token)
                                .clickEvent(ClickEvent.clickEvent(
                                        ClickEvent.Action.SUGGEST_COMMAND, token
                                ))
                        )
                );
    }

    private static final Component botNotFound = Component
            .translatable("mcapi.bot.notfound")
            .color(errorColor);

    @Override
    public Component botNotFound() {
        return botNotFound;
    }

    @Override
    public Component playerNotFound(String name) {
        return Component
                .translatable("mcapi.player.notfound")
                .args(Component.text(name))
                .color(errorColor);
    }

    private final Component onlyForPlayers = Component
            .translatable("mcapi.player.only")
            .color(errorColor);

    @Override
    public Component onlyForPlayers() {
        return onlyForPlayers;
    }

    @Override
    public Component botNameUsed() {
        return Component
                .translatable("mcapi.bot.name.used")
                .color(errorColor);
    }

    @Override
    public Component botNameTooLong() {
        return Component
                .translatable("mcapi.bot.name.toolong")
                .color(errorColor);
    }

    @Override
    public Component stringfulFailedToParse(int argumentId) {
        return Component
                .translatable("mcapi.stringful.failed.parse")
                .args(Component.text(argumentId))
                .color(errorColor);
    }

    @Override
    public Component stringfulNotEnoughArguments() {
        return Component
                .translatable("mcapi.stringful.failed.notenough")
                .color(errorColor);
    }

    @Override
    public Component botDeleteNotify() {
        return Component.empty()
                .append(warning())
                .append(Component.space())
                .append(Component
                        .translatable("mcapi.bot.delete.notify")
                        .color(defaultColor)
                );
    }

    @Override
    public Component page(int page) {
        return Component
                .translatable("mcapi.page")
                .color(defaultColor)
                .args(Component.text(page));
    }

    @Override
    public Component botList(Collection<? extends BotEntity> botEntities, String playerName, int page) {
        return Component
                .translatable("mcapi.bot.list.header")
                .args(Component.text(playerName))
                .color(defaultColor)
                .append(list(
                        botEntities
                                .stream()
                                .map(it -> Component
                                        .text(it.getName())
                                        .append(Component.space())
                                        .append(dash)
                                        .append(Component.space())
                                        .append(Component
                                                .text(it.getToken())
                                                .clickEvent(ClickEvent.suggestCommand(it.getToken()))
                                        )
                                )
                                .collect(Collectors.toList())
                ))
                .append(Component.newline())
                .append(page(page));
    }

    @Override
    public Component permissionList(Collection<? extends BotPermissionEntity> permissionEntities, String botName, int page) {
        return Component
                .translatable("mcapi.permission.list.header")
                .args(Component.text(botName))
                .color(defaultColor)
                .append(list(
                        permissionEntities
                                .stream()
                                .map(it -> Component
                                        .text(it.toLocalPermission())
                                        .color(it.isToggled() ? successColor : errorColor)
                                )
                                .collect(Collectors.toList())
                ))
                .append(Component.newline())
                .append(page(page));
    }

    @Override
    public Component linksList(Collection<Pair<BotLinkEntity, BotEntity>> linkEntities, String playerName, int page) {
        return Component
                .translatable("mcapi.links.list.header")
                .args(Component.text(playerName))
                .color(defaultColor)
                .append(list(
                        linkEntities
                                .stream()
                                .map(it -> Component
                                        .text(it.getRight().getName())
                                        .append(Component.space())
                                        .append(inBrackets(Component
                                                .translatable("mcapi.links.list.permissions")
                                                .color(neutralColor)
                                                .clickEvent(ClickEvent.runCommand(
                                                        "/mcapi linkMenu permission " + it.getLeft().getBotId()
                                                ))
                                        ))
                                        .append(Component.space())
                                        .append(inBrackets(Component
                                                .translatable("mcapi.links.list.unlink")
                                                .color(errorColor)
                                                .clickEvent(ClickEvent.runCommand(
                                                        "/mcapi linkMenu unlink " + it.getLeft().getBotId()
                                                ))
                                        ))
                                )
                                .collect(Collectors.toList())
                ))
                .append(Component.newline())
                .append(page(page));
    }

    private final Component provideToken = Component
            .translatable("mcapi.bot.token.provide")
            .color(errorColor);

    @Override
    public Component provideToken() {
        return provideToken;
    }

    private static Component inBrackets(Component component) {
        return Component.empty()
                .color(component.color())
                .append(Component.text("["))
                .append(component)
                .append(Component.text("]"));
    }

    private static Component stringList(Collection<? extends String> list) {
        return stringList(list, Component.newline());
    }

    private static Component stringList(Collection<? extends String> list, Component delimiter) {
        return list(
                list
                        .stream()
                        .map(Component::text)
                        .collect(Collectors.toList()),
                delimiter
        );
    }

    private static Component list(Collection<? extends Component> list) {
        return list(list, Component.newline());
    }

    private static Component list(Collection<? extends Component> list, Component delimiter) {
        Component result = Component.empty();
        for (Component component : list) {
            result = result
                    .append(delimiter)
                    .append(dash
                            .append(Component.space())
                            .append(component.colorIfAbsent(defaultColor))
                    );
        }
        return result;
    }
}

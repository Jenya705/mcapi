package com.github.jenya705.mcapi.server.module.config.message;

import com.google.inject.Singleton;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
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

    @Override
    public Component playerNotFound(String name) {
        return Component
                .translatable("mcapi.player.notfound")
                .args(Component.text(name))
                .color(errorColor);
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
                    .append(Component
                            .text("- ")
                            .color(signColor)
                            .append(component.colorIfAbsent(defaultColor))
                    );
        }
        return result;
    }
}

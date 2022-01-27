package com.github.jenya705.mcapi.server.module.config.message;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.player.Player;
import com.google.inject.ImplementedBy;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;

import java.util.Collection;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Jenya705
 */
@ImplementedBy(DefaultMessageContainer.class)
public interface MessageContainer {

    default Component render(Component component, Locale locale) {
        return GlobalTranslator.render(component, Objects.requireNonNullElse(locale, Locale.ENGLISH));
    }

    default Component render(Component component, Player sender) {
        return render(component, sender.getLocale());
    }

    default Component render(Component component, CommandSender sender) {
        return render(component, sender instanceof Player ? ((Player) sender).getLocale() : null);
    }

    Component commandHelp(Collection<? extends String> commands, String commandStart);

    Component notPermitted();

    Component success();

    Component botCreation(String token);

    Component playerNotFound(String name);

    Component botNameUsed();

    Component botNameTooLong();

}

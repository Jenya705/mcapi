package com.github.jenya705.mcapi.server.module.config.message;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.player.Player;
import net.kyori.adventure.text.Component;

import java.util.Locale;

public interface LocaleMessage {

    Component getMessage(Locale locale);

    default Component getMessage(CommandSender sender) {
        if (sender instanceof Player) {
            return getMessage(((Player) sender).getLocale());
        }
        return getMessage(Locale.ENGLISH);
    }

}

package com.github.jenya705.mcapi;

import net.kyori.adventure.text.Component;

/**
 * @author Jenya705
 */
public interface CommandSender {

    NamespacedKey CONSOLE_TYPE = NamespacedKey.minecraft("console");

    NamespacedKey UNDEFINED_TYPE = NamespacedKey.minecraft("undefined");

    NamespacedKey getType();

    String getId();

    void sendMessage(String message);

    void sendMessage(Component component);

    boolean hasPermission(String permission);
}

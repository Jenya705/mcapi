package com.github.jenya705.mcapi;

import net.kyori.adventure.text.Component;

/**
 * @author Jenya705
 */
public interface CommandSender {

    String getType();

    String getId();

    void sendMessage(String message);

    void sendMessage(Component component);

    boolean hasPermission(String permission);
}

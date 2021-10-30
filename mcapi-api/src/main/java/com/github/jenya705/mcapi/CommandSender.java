package com.github.jenya705.mcapi;

/**
 * @author Jenya705
 */
public interface CommandSender {

    String getType();

    String getId();

    void sendMessage(String message);

    boolean hasPermission(String permission);
}

package com.github.jenya705.mcapi;

/**
 * @author Jenya705
 */
public interface ApiCommandSender {

    void sendMessage(String message);

    boolean hasPermission(String permission);
}

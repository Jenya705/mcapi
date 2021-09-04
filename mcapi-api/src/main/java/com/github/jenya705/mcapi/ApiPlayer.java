package com.github.jenya705.mcapi;

/**
 * @author Jenya705
 */
public interface ApiPlayer extends ApiCommandSender, ApiOfflinePlayer {

    void kick(String reason);

}

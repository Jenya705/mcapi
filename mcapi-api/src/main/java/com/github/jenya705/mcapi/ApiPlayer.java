package com.github.jenya705.mcapi;

import java.util.Set;
import java.util.UUID;

/**
 * @author Jenya705
 */
public interface ApiPlayer extends ApiCommandSender, ApiOfflinePlayer {

    void kick(String reason);

}

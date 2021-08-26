package com.github.jenya705.mcapi;

import java.util.Set;
import java.util.UUID;

/**
 * @author Jenya705
 */
public interface ApiPlayer extends ApiCommandSender {

    String getName();

    UUID getUuid();

    void kick(String reason);

    void ban(String reason);

}

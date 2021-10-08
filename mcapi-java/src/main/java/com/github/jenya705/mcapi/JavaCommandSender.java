package com.github.jenya705.mcapi;

import net.kyori.adventure.text.Component;

/**
 * @author Jenya705
 */
public interface JavaCommandSender extends CommandSender {

    void sendMessage(Component component);
}

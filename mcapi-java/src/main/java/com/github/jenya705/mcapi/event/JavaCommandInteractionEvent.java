package com.github.jenya705.mcapi.event;

import com.github.jenya705.mcapi.JavaCommandSender;

/**
 * @author Jenya705
 */
public interface JavaCommandInteractionEvent extends CommandInteractionEvent {

    @Override
    JavaCommandSender getSender();

}

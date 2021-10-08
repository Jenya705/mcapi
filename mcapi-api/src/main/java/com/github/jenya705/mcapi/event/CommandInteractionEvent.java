package com.github.jenya705.mcapi.event;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.command.CommandInteractionValue;

/**
 * @author Jenya705
 */
public interface CommandInteractionEvent {

    String getPath();

    CommandInteractionValue[] getValues();

    CommandSender getSender();
}

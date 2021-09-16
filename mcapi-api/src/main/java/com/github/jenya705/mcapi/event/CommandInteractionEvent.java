package com.github.jenya705.mcapi.event;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.command.ApiCommandInteractionValue;

/**
 * @author Jenya705
 */
public interface CommandInteractionEvent {

    String getPath();

    ApiCommandInteractionValue[] getValues();

    ApiCommandSender getSender();
}

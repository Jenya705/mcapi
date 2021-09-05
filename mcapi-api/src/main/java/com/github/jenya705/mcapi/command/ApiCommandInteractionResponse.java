package com.github.jenya705.mcapi.command;

import com.github.jenya705.mcapi.ApiCommandSender;

/**
 * @author Jenya705
 */
public interface ApiCommandInteractionResponse {

    String getPath();

    ApiCommandInteractionValue[] getValues();

    ApiCommandSender getSender();
}

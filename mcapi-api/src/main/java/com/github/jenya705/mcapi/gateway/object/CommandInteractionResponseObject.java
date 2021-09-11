package com.github.jenya705.mcapi.gateway.object;

import com.github.jenya705.mcapi.command.ApiCommandInteractionResponse;
import com.github.jenya705.mcapi.gateway.GatewayObject;

/**
 * @author Jenya705
 */
public class CommandInteractionResponseObject {

    public static final String type = "command_interaction";

    public static GatewayObject<ApiCommandInteractionResponse> of(ApiCommandInteractionResponse response) {
        return new GatewayObject<>(
                type, response
        );
    }
}

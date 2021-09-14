package com.github.jenya705.mcapi.entity.command;

import com.github.jenya705.mcapi.command.ApiCommandInteractionResponse;
import com.github.jenya705.mcapi.entity.RestCommandSender;

import java.util.Arrays;

/**
 * @author Jenya705
 */
public class RestCommandInteractionResponse {

    private String path;
    private RestCommandInteractionValue[] values;
    private RestCommandSender commandSender;

    public static RestCommandInteractionResponse from(ApiCommandInteractionResponse response) {
        return new RestCommandInteractionResponse(
                response.getPath(),
                Arrays
                        .stream(response.getValues())
                        .map(RestCommandInteractionValue::from)
                        .toArray(RestCommandInteractionValue[]::new),
                RestCommandSender.from(response.getSender())
        );
    }
}

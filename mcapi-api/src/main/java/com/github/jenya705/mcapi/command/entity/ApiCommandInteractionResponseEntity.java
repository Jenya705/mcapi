package com.github.jenya705.mcapi.command.entity;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.command.ApiCommandInteractionResponse;
import com.github.jenya705.mcapi.command.ApiCommandInteractionValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiCommandInteractionResponseEntity implements ApiCommandInteractionResponse {

    private String path;
    private ApiCommandInteractionValue[] values;
    private ApiCommandSender sender;
}

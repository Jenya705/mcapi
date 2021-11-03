package com.github.jenya705.mcapi.rest;

import com.github.jenya705.mcapi.CommandSender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestCommandSender {

    private String type;
    private String id;

    public static RestCommandSender from(CommandSender sender) {
        return new RestCommandSender(
                sender.getType(),
                sender.getId()
        );
    }
}

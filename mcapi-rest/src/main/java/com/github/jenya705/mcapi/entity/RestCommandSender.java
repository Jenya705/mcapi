package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.ApiCommandSender;
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
    private String name;

    public static RestCommandSender from(ApiCommandSender sender) {
        return new RestCommandSender(
                sender.getType(),
                sender.getName()
        );
    }

}

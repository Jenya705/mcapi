package com.github.jenya705.mcapi.module.message;

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
public class DefaultSendMessage implements SendMessage {

    private String message;

    @Override
    public void send(ApiCommandSender sender) {
        sender.sendMessage(message);
    }
}

package com.github.jenya705.mcapi.form;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.module.message.SendMessage;
import lombok.AllArgsConstructor;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class FormMessage implements SendMessage {

    private final Form form;

    private final FormPlatformProvider formProvider;

    @Override
    public void send(ApiCommandSender sender) {
        formProvider.sendMessage(sender, form);
    }
}

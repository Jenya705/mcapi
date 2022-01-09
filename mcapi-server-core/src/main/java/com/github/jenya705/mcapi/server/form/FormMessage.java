package com.github.jenya705.mcapi.server.form;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.server.module.message.Message;
import lombok.AllArgsConstructor;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class FormMessage implements Message {

    private final Form form;

    private final FormPlatformProvider formProvider;

    @Override
    public void send(CommandSender sender) {
        formProvider.sendMessage(sender, form);
    }
}

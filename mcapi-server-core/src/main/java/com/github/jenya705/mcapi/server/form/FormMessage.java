package com.github.jenya705.mcapi.server.form;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.server.module.message.Message;
import com.github.jenya705.mcapi.server.module.message.TypedMessage;
import com.github.jenya705.mcapi.server.module.message.TypedMessageImpl;
import lombok.AllArgsConstructor;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class FormMessage implements Message {

    public static final String type = "form";

    private final Form form;

    private final FormPlatformProvider formProvider;

    @Override
    public void send(CommandSender sender) {
        formProvider.sendMessage(sender, form);
    }

    @Override
    public TypedMessage type() {
        return new TypedMessageImpl(type, this);
    }
}

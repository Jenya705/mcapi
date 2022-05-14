package com.github.jenya705.mcapi.server.module.message;

import com.github.jenya705.mcapi.CommandSender;
import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class ComponentMessage implements Message {

    public static final String type = "component";

    private final Component component;

    @Override
    public void send(CommandSender sender) {
        sender.sendMessage(component);
    }

    @Override
    public TypedMessage type() {
        return new TypedMessageImpl(type, this);
    }
}

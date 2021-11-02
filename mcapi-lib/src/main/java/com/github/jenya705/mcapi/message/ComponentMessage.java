package com.github.jenya705.mcapi.message;

import com.github.jenya705.mcapi.Message;
import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class ComponentMessage implements Message {

    private final Component component;

    @Override
    public String getType() {
        return "component";
    }

    @Override
    public Object getMessage() {
        return component;
    }
}

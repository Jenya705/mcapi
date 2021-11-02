package com.github.jenya705.mcapi.message;

import com.github.jenya705.mcapi.Message;
import lombok.AllArgsConstructor;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class DefaultMessage implements Message {

    private final String message;

    @Override
    public String getType() {
        return "default";
    }

    @Override
    public Object getMessage() {
        return message;
    }
}

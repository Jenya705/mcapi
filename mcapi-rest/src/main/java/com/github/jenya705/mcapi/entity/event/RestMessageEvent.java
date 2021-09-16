package com.github.jenya705.mcapi.entity.event;

import com.github.jenya705.mcapi.event.MessageEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestMessageEvent {

    public static final String type = "message";

    private String message;
    private UUID author;

    public String getType() {
        return type;
    }

    public static RestMessageEvent from(MessageEvent event) {
        return new RestMessageEvent(
                event.getMessage(),
                event.getAuthor().getUuid()
        );
    }
}

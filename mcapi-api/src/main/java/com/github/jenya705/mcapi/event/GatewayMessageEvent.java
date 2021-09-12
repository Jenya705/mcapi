package com.github.jenya705.mcapi.event;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.gateway.GatewayObject;
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
public class GatewayMessageEvent {

    public static final String type = "message_received";

    private String message;
    private UUID author;

    public static GatewayObject<GatewayMessageEvent> of(String message, ApiPlayer sender) {
        return new GatewayObject<>(
                type,
                new GatewayMessageEvent(
                        message,
                        sender == null ? null : sender.getUuid()
                )
        );
    }
}

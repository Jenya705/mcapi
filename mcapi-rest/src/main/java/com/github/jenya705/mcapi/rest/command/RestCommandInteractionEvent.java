package com.github.jenya705.mcapi.rest.command;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.event.CommandInteractionEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestCommandInteractionEvent {

    public static final String type = "command_interaction";

    private String path;
    private RestCommandInteractionValue[] values;
    private CommandSender commandSender;

    public String getType() {
        return type;
    }

    public static RestCommandInteractionEvent from(CommandInteractionEvent event) {
        return new RestCommandInteractionEvent(
                event.getPath(),
                Arrays
                        .stream(event.getValues())
                        .map(RestCommandInteractionValue::from)
                        .toArray(RestCommandInteractionValue[]::new),
                event.getSender()
        );
    }
}

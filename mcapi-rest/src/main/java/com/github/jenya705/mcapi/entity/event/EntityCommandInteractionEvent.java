package com.github.jenya705.mcapi.entity.event;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.command.CommandInteractionValue;
import com.github.jenya705.mcapi.event.CommandInteractionEvent;
import com.github.jenya705.mcapi.rest.command.RestCommandInteractionEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityCommandInteractionEvent implements CommandInteractionEvent {

    private String path;
    private CommandSender sender;
    private CommandInteractionValue[] values;

    public RestCommandInteractionEvent rest() {
        return RestCommandInteractionEvent.from(this);
    }
}

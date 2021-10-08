package com.github.jenya705.mcapi.entity.api.event;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.command.CommandInteractionValue;
import com.github.jenya705.mcapi.entity.event.RestCommandInteractionEvent;
import com.github.jenya705.mcapi.event.CommandInteractionEvent;
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

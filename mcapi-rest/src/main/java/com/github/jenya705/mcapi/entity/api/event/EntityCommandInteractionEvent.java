package com.github.jenya705.mcapi.entity.api.event;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.command.ApiCommandInteractionValue;
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
    private ApiCommandSender sender;
    private ApiCommandInteractionValue[] values;

    public RestCommandInteractionEvent rest() {
        return RestCommandInteractionEvent.from(this);
    }

}

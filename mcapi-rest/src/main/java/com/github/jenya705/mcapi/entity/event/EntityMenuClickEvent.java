package com.github.jenya705.mcapi.entity.event;

import com.github.jenya705.mcapi.event.MenuClickEvent;
import com.github.jenya705.mcapi.player.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityMenuClickEvent implements MenuClickEvent {

    private String id;
    private Player player;
}

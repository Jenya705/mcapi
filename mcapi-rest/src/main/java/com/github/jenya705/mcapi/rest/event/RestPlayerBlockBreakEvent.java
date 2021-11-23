package com.github.jenya705.mcapi.rest.event;

import com.github.jenya705.mcapi.event.PlayerBlockBreakEvent;
import com.github.jenya705.mcapi.rest.block.RestBlock;
import com.github.jenya705.mcapi.rest.player.RestPlayer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestPlayerBlockBreakEvent {

    public static final String type = "block_break";

    private RestPlayer player;
    private RestBlock brokenBlock;

    public String getType() {
        return type;
    }

    public static RestPlayerBlockBreakEvent from(PlayerBlockBreakEvent event) {
        return new RestPlayerBlockBreakEvent(
                RestPlayer.from(event.getPlayer()),
                RestBlock.from(event.getBrokenBlock())
        );
    }

}

package com.github.jenya705.mcapi.rest.event;

import com.github.jenya705.mcapi.event.PlayerBlockPlaceEvent;
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
public class RestPlayerBlockPlaceEvent {

    public static final String type = "player_block_place";

    private RestPlayer player;
    private RestBlock placedBlock;

    public String getType() {
        return type;
    }

    public static RestPlayerBlockPlaceEvent from(PlayerBlockPlaceEvent event) {
        return new RestPlayerBlockPlaceEvent(
                RestPlayer.from(event.getPlayer()),
                RestBlock.from(event.getPlacedBlock())
        );
    }


}

package com.github.jenya705.mcapi.event;

import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.player.Player;

/**
 * @author Jenya705
 */
public interface PlayerBlockPlaceEvent {

    Player getPlayer();

    Block getPlacedBlock();

}

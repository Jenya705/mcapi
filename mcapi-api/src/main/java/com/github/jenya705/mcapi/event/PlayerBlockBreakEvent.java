package com.github.jenya705.mcapi.event;

import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.player.Player;

/**
 * @author Jenya705
 */
public interface PlayerBlockBreakEvent {

    Player getPlayer();

    Block getBrokenBlock();

}

package com.github.jenya705.mcapi.entity.event;

import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.event.PlayerBlockBreakEvent;
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
public class EntityPlayerBlockBreakEvent implements PlayerBlockBreakEvent {

    private Player player;
    private Block brokenBlock;

}

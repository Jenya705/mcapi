package com.github.jenya705.mcapi.entity.block;

import com.github.jenya705.mcapi.Player;
import com.github.jenya705.mcapi.block.BlockFace;
import com.github.jenya705.mcapi.block.data.Chest;
import com.github.jenya705.mcapi.inventory.Inventory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityChest implements Chest {

    private List<? extends Player> watchers;
    private Inventory inventory;
    private BlockFace direction;

}

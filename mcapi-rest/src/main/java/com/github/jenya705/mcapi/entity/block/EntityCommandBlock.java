package com.github.jenya705.mcapi.entity.block;

import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.block.BlockFace;
import com.github.jenya705.mcapi.block.data.CommandBlock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityCommandBlock implements CommandBlock {

    private Block block;
    private String command;
    private BlockFace direction;

}

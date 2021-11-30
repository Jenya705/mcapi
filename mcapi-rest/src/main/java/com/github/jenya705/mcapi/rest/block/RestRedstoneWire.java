package com.github.jenya705.mcapi.rest.block;

import com.github.jenya705.mcapi.block.BlockFace;
import com.github.jenya705.mcapi.block.data.RedstoneWire;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestRedstoneWire {

    private int power;
    private String west;
    private String south;
    private String east;
    private String north;

    public static RestRedstoneWire from(RedstoneWire wire) {
        return new RestRedstoneWire(
                wire.getPower(),
                wire.getConnection(BlockFace.WEST).name(),
                wire.getConnection(BlockFace.SOUTH).name(),
                wire.getConnection(BlockFace.EAST).name(),
                wire.getConnection(BlockFace.NORTH).name()
        );
    }
}

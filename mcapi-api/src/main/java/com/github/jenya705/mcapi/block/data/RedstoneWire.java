package com.github.jenya705.mcapi.block.data;

import com.github.jenya705.mcapi.block.BlockData;
import com.github.jenya705.mcapi.block.BlockFace;
import com.github.jenya705.mcapi.block.LevelPowerable;

/**
 * @author Jenya705
 */
public interface RedstoneWire extends BlockData, LevelPowerable {

    enum Connection {

        UP,
        SIDE,
        NONE

    }

    Connection getConnection(BlockFace face);

    void setConnection(BlockFace face, Connection connection);

}

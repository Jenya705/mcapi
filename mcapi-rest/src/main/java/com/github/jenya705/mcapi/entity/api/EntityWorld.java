package com.github.jenya705.mcapi.entity.api;

import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.world.World;
import com.github.jenya705.mcapi.world.WorldDimension;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityWorld implements World {

    private String name;
    private WorldDimension worldDimension;

    @Override
    public Block getBlock(Location location) {
        EntityUtils.throwEntityContextException();
        return null;
    }

    @Override
    public Block getBlock(int x, int y, int z) {
        EntityUtils.throwEntityContextException();
        return null;
    }
}

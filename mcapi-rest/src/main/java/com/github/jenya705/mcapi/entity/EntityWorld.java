package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.world.World;
import com.github.jenya705.mcapi.world.WorldDimension;
import com.github.jenya705.mcapi.world.WorldWeather;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityWorld implements World {

    private String name;
    private WorldDimension worldDimension;
    private WorldWeather worldWeather;

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

    @Override
    public Collection<Entity> getEntities() {
        EntityUtils.throwEntityContextException();
        return null;
    }
}

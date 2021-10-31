package com.github.jenya705.mcapi.entity.api;

import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.entity.RestLocation;
import com.github.jenya705.mcapi.world.World;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityLocation implements Location {

    private double x;
    private double y;
    private double z;
    private World world;

    public RestLocation rest() {
        return RestLocation.from(this);
    }

    public World getWorld() {
        EntityUtils.throwEntityContextException();
        return null;
    }
}

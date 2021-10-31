package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.RestClient;
import com.github.jenya705.mcapi.entity.api.EntityLocation;
import lombok.experimental.UtilityClass;

/**
 * @author Jenya705
 */
@UtilityClass
public class LazyLocation {

    public Location of(RestClient restClient, RestLocation location) {
        return new EntityLocation(
                location.getX(),
                location.getY(),
                location.getZ(),
                LazyWorld.of(restClient, location.getWorld())
        );
    }

}

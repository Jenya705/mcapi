package com.github.jenya705.mcapi.rest;

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
public class RestWorld {

    private String name;
    private String dimension;
    private String weather;

    public static RestWorld from(World world) {
        return new RestWorld(
                world.getId().toString(),
                world.getWorldDimension().name(),
                world.getWorldWeather().name()
        );
    }

}

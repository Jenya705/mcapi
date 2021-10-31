package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestLocation {

    private String world;
    private double x;
    private double y;
    private double z;

    public static RestLocation from(Location location) {
        return new RestLocation(
                location.getWorld().getName(),
                location.getX(),
                location.getY(),
                location.getZ()
        );
    }
}

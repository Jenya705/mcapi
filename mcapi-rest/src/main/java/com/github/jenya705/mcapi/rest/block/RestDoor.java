package com.github.jenya705.mcapi.rest.block;

import com.github.jenya705.mcapi.block.data.Door;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestDoor {

    private boolean open;
    private boolean powered;
    private String half;
    private String direction;
    private String hinge;

    public static RestDoor from(Door door) {
        return new RestDoor(
                door.isOpen(),
                door.isPowered(),
                door.getHalf().name(),
                door.getDirection().name(),
                door.getHinge().name()
        );
    }
}

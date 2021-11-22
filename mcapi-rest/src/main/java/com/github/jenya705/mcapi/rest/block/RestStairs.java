package com.github.jenya705.mcapi.rest.block;

import com.github.jenya705.mcapi.block.data.Stairs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestStairs {

    private String direction;
    private boolean waterlogged;
    private String shape;
    private String half;

    public static RestStairs from(Stairs stairs) {
        return new RestStairs(
                stairs.getDirection().name(),
                stairs.isWaterlogged(),
                stairs.getShape().name(),
                stairs.getHalf().name()
        );
    }

}

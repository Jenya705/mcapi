package com.github.jenya705.mcapi.rest.block;

import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.rest.RestLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestBlock {

    private RestLocation location;
    private String material;

    public static RestBlock from(Block block) {
        return new RestBlock(
                RestLocation.from(block.getLocation()),
                block.getMaterial().getKey().toString()
        );
    }

}

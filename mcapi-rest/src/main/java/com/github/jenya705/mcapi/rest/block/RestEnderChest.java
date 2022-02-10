package com.github.jenya705.mcapi.rest.block;

import com.github.jenya705.mcapi.block.data.EnderChest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestEnderChest {

    private RestBlock block;
    private boolean waterlogged;
    private String direction;

    public static RestEnderChest from(EnderChest enderChest) {
        return new RestEnderChest(
                RestBlock.from(enderChest.getBlock()),
                enderChest.isWaterlogged(),
                enderChest.getDirection().name()
        );
    }

}

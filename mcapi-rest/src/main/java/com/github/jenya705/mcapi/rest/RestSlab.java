package com.github.jenya705.mcapi.rest;

import com.github.jenya705.mcapi.block.data.Slab;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestSlab {

    private String type;
    private boolean waterlogged;

    public static RestSlab from(Slab slab) {
        return new RestSlab(
                slab.getType().name(),
                slab.isWaterlogged()
        );
    }

}

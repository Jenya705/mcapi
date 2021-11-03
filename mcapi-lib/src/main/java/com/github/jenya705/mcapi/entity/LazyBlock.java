package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.Material;
import com.github.jenya705.mcapi.RestClient;
import com.github.jenya705.mcapi.VanillaMaterial;
import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.block.BlockData;
import com.github.jenya705.mcapi.rest.block.RestBlock;
import lombok.Builder;
import lombok.Getter;

/**
 * @author Jenya705
 */
@Builder
public class LazyBlock implements Block {

    public static LazyBlock of(RestClient restClient, RestBlock block) {
        return LazyBlock
                .builder()
                .restClient(restClient)
                .location(LazyLocation.of(restClient, block.getLocation()))
                .material(VanillaMaterial.getMaterial(block.getMaterial()))
                .build();
    }

    private final RestClient restClient;
    @Getter
    private final Location location;
    @Getter
    private final Material material;

    @Override
    public BlockData getBlockData() {
        return restClient
                .getBlockData(
                        location.getWorld().getName(),
                        (int) location.getX(),
                        (int) location.getY(),
                        (int) location.getZ(),
                        getMaterial().getKey()
                ).block();
    }
}

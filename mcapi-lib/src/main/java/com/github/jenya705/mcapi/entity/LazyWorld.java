package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.RestClient;
import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.world.World;
import com.github.jenya705.mcapi.world.WorldDimension;
import lombok.Builder;
import lombok.Getter;

/**
 * @author Jenya705
 */
@Builder
public class LazyWorld implements World {

    public static World of(RestClient restClient, String name) {
        return LazyWorld
                .builder()
                .restClient(restClient)
                .name(name)
                .build();
    }

    public static World of(RestClient restClient, RestWorld world) {
        return LazyWorld
                .builder()
                .restClient(restClient)
                .name(world.getName())
                .dimension(WorldDimension.valueOf(world.getDimension()))
                .build();
    }

    private final RestClient restClient;
    @Getter
    private final String name;

    private WorldDimension dimension;

    @Override
    public Block getBlock(Location location) {
        return getBlock(
                (int) location.getX(),
                (int) location.getY(),
                (int) location.getZ()
        );
    }

    @Override
    public Block getBlock(int x, int y, int z) {
        return restClient.getBlock(
                name, x, y, z
        ).block();
    }

    @Override
    public WorldDimension getWorldDimension() {
        if (dimension == null) {
            loadFullWorld();
        }
        return dimension;
    }

    private void loadFullWorld() {
        World world = restClient
                .getWorld(name)
                .blockOptional()
                .orElseThrow();
        dimension = world.getWorldDimension();
    }
}

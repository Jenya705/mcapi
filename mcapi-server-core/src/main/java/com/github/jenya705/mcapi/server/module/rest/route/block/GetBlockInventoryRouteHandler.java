package com.github.jenya705.mcapi.server.module.rest.route.block;

import com.github.jenya705.mcapi.NamespacedKey;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.error.BlockDataNotFoundException;
import com.github.jenya705.mcapi.error.BlockNotFoundException;
import com.github.jenya705.mcapi.error.WorldNotFoundException;
import com.github.jenya705.mcapi.inventory.InventoryHolder;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.github.jenya705.mcapi.server.util.ReactorUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author Jenya705
 */
@Singleton
public class GetBlockInventoryRouteHandler extends AbstractRouteHandler {

    @Inject
    public GetBlockInventoryRouteHandler(ServerApplication application) {
        super(application, Routes.BLOCK_INVENTORY);
    }

    @Override
    public Mono<Response> handle(Request request) {
        NamespacedKey worldId = request.paramOrException("id", NamespacedKey.class);
        int x = request.paramOrException("x", int.class);
        int y = request.paramOrException("y", int.class);
        int z = request.paramOrException("z", int.class);
        request
                .bot()
                .needPermission(Permissions.BLOCK_INVENTORY_GET);
        return core()
                .getWorld(worldId)
                .flatMap(world -> ReactorUtils.ifNullError(world, () -> WorldNotFoundException.create(worldId)))
                .map(world -> world.getBlock(x, y, z))
                .flatMap(block -> ReactorUtils.ifNullError(block, BlockNotFoundException::create))
                .map(Block::getBlockData)
                .flatMap(blockData -> ReactorUtils.ifFalseError(
                        blockData, blockData instanceof InventoryHolder, BlockDataNotFoundException::create
                ))
                .map(blockData -> ((InventoryHolder) blockData).getInventory())
                .map(inventory -> Response.create().ok(inventory));
    }
}

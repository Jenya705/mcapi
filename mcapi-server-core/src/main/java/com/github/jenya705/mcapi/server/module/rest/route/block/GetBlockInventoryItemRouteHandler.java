package com.github.jenya705.mcapi.server.module.rest.route.block;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.error.BlockNotFoundException;
import com.github.jenya705.mcapi.error.WorldNotFoundException;
import com.github.jenya705.mcapi.inventory.InventoryHolder;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.github.jenya705.mcapi.server.util.ItemUtils;
import com.github.jenya705.mcapi.world.World;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Optional;

/**
 * @author Jenya705
 */
@Singleton
public class GetBlockInventoryItemRouteHandler extends AbstractRouteHandler {

    @Inject
    public GetBlockInventoryItemRouteHandler(ServerApplication application) {
        super(application, Routes.BLOCK_INVENTORY_ITEM);
    }

    @Override
    public void handle(Request request, Response response) throws Exception {
        World world = request.paramOrException("id", World.class);
        int x = request.paramOrException("x", int.class);
        int y = request.paramOrException("y", int.class);
        int z = request.paramOrException("z", int.class);
        int item = request.paramOrException("item", int.class);
        request
                .bot()
                .needPermission(Permissions.BLOCK_INVENTORY_GET);
        response.ok(
                Optional.of(world)
                        .map(it -> it.getBlock(x, y, z))
                        .map(Block::getBlockData)
                        .filter(it -> it instanceof InventoryHolder)
                        .map(it -> (InventoryHolder) it)
                        .map(InventoryHolder::getInventory)
                        .map(it ->
                                Optional
                                        .ofNullable(it.getItem(item))
                                        .orElse(ItemUtils.empty())
                        )
                        .orElseThrow(BlockNotFoundException::create)
        );
    }
}

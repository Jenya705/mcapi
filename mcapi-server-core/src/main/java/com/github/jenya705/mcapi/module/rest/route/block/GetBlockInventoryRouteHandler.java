package com.github.jenya705.mcapi.module.rest.route.block;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.error.BlockDataNotFoundException;
import com.github.jenya705.mcapi.error.BlockNotFoundException;
import com.github.jenya705.mcapi.error.WorldNotFoundException;
import com.github.jenya705.mcapi.inventory.InventoryHolder;
import com.github.jenya705.mcapi.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.module.web.Response;
import com.github.jenya705.mcapi.permission.Permissions;

import java.util.Optional;

/**
 * @author Jenya705
 */
public class GetBlockInventoryRouteHandler extends AbstractRouteHandler {

    public GetBlockInventoryRouteHandler() {
        super(Routes.BLOCK_INVENTORY);
    }

    @Override
    public void handle(Request request, Response response) throws Exception {
        String id = request.paramOrException("id");
        int x = request.paramOrException("x", int.class);
        int y = request.paramOrException("y", int.class);
        int z = request.paramOrException("z", int.class);
        request
                .bot()
                .needPermission(Permissions.BLOCK_INVENTORY_GET);
        InventoryHolder inventoryHolderBlock =
                Optional.ofNullable(
                        Optional.ofNullable(
                                core()
                                        .getOptionalWorld(id)
                                        .orElseThrow(() -> WorldNotFoundException.create(id))
                        )
                                .map(it -> it.getBlock(x, y, z))
                                .orElseThrow(BlockNotFoundException::create)
                )
                        .map(Block::getBlockData)
                        .filter(it -> it instanceof InventoryHolder)
                        .map(it -> (InventoryHolder) it)
                        .orElseThrow(BlockDataNotFoundException::create);
        response.ok(inventoryHolderBlock.getInventory());
    }
}

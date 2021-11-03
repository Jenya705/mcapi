package com.github.jenya705.mcapi.module.rest.route.block;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.error.BlockNotFoundException;
import com.github.jenya705.mcapi.error.WorldNotFoundException;
import com.github.jenya705.mcapi.inventory.InventoryHolder;
import com.github.jenya705.mcapi.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.module.web.Response;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.util.ItemUtils;

import java.util.Optional;

/**
 * @author Jenya705
 */
public class GetBlockInventoryItemRouteHandler extends AbstractRouteHandler {

    public GetBlockInventoryItemRouteHandler() {
        super(Routes.BLOCK_INVENTORY_ITEM);
    }

    @Override
    public void handle(Request request, Response response) throws Exception {
        String id = request.paramOrException("id");
        int x = request.paramOrException("x", int.class);
        int y = request.paramOrException("y", int.class);
        int z = request.paramOrException("z", int.class);
        int item = request.paramOrException("item", int.class);
        request
                .bot()
                .needPermission(Permissions.BLOCK_INVENTORY_GET);
        response.ok(
                Optional.ofNullable(
                        core()
                                .getOptionalWorld(id)
                                .orElseThrow(() -> WorldNotFoundException.create(id))
                )
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

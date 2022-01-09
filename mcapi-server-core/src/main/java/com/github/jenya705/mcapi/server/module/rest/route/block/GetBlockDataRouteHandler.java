package com.github.jenya705.mcapi.server.module.rest.route.block;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.error.BlockDataNotFoundException;
import com.github.jenya705.mcapi.error.BlockNotFoundException;
import com.github.jenya705.mcapi.error.WorldNotFoundException;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.github.jenya705.mcapi.server.util.PermissionUtils;

import java.util.Optional;

/**
 * @author Jenya705
 */
public class GetBlockDataRouteHandler extends AbstractRouteHandler {

    public GetBlockDataRouteHandler() {
        super(Routes.BLOCK_DATA);
    }

    @Override
    public void handle(Request request, Response response) throws Exception {
        String id = request.paramOrException("id");
        int x = request.paramOrException("x", int.class);
        int y = request.paramOrException("y", int.class);
        int z = request.paramOrException("z", int.class);
        Block block = Optional.ofNullable(
                core()
                        .getOptionalWorld(id)
                        .orElseThrow(() -> WorldNotFoundException.create(id))
                        .getBlock(x, y, z)
        ).orElseThrow(BlockNotFoundException::create);
        request
                .bot()
                .needPermission(PermissionUtils.getData(block));
        response.ok(
                Optional
                        .ofNullable(block.getBlockData())
                        .orElseThrow(BlockDataNotFoundException::create)
        );
    }
}

package com.github.jenya705.mcapi.server.module.rest.route.block;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.block.BlockData;
import com.github.jenya705.mcapi.error.BlockNotFoundException;
import com.github.jenya705.mcapi.error.WorldNotFoundException;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.module.block.BlockDataModule;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.github.jenya705.mcapi.server.util.PermissionUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Optional;

/**
 * @author Jenya705
 */
@Singleton
public class SetBlockDataFieldRouteHandler extends AbstractRouteHandler {

    private final BlockDataModule blockDataModule;

    @Inject
    public SetBlockDataFieldRouteHandler(ServerApplication application, BlockDataModule blockDataModule) {
        super(application, Routes.BLOCK_DATA_FIELD);
        this.blockDataModule = blockDataModule;
    }

    @Override
    public void handle(Request request, Response response) throws Exception {
        String id = request.paramOrException("id");
        int x = request.paramOrException("x", int.class);
        int y = request.paramOrException("y", int.class);
        int z = request.paramOrException("z", int.class);
        String field = request.paramOrException("name");
        Block block = Optional.ofNullable(
                core()
                        .getOptionalWorld(id)
                        .orElseThrow(() -> WorldNotFoundException.create(id))
                        .getBlock(x, y, z)
        )
                .orElseThrow(BlockNotFoundException::create);
        request
                .bot()
                .needPermission(PermissionUtils.updateData(block));
        blockDataModule
                .setField(block.getBlockData(), field, request.bodyOrException());
        response.noContent();
    }
}

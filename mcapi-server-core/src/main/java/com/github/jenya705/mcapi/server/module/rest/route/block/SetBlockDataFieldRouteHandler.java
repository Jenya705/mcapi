package com.github.jenya705.mcapi.server.module.rest.route.block;

import com.github.jenya705.mcapi.NamespacedKey;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.error.BlockNotFoundException;
import com.github.jenya705.mcapi.error.WorldNotFoundException;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.module.block.BlockDataModule;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.github.jenya705.mcapi.server.util.PermissionUtils;
import com.github.jenya705.mcapi.server.util.ReactorUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import reactor.core.publisher.Mono;

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
    public Mono<Response> handle(Request request) {
        NamespacedKey worldId = request.paramOrException("id", NamespacedKey.class);
        int x = request.paramOrException("x", int.class);
        int y = request.paramOrException("y", int.class);
        int z = request.paramOrException("z", int.class);
        String field = request.paramOrException("name");
        return core()
                .getWorld(worldId)
                .flatMap(world -> ReactorUtils.ifNullError(
                        world, () -> WorldNotFoundException.create(worldId)))
                .map(world -> world.getBlock(x, y, z))
                .flatMap(block -> ReactorUtils.ifNullError(block, BlockNotFoundException::create))
                .doOnNext(block -> request.bot().needPermission(PermissionUtils.updateData(block)))
                .doOnNext(block -> blockDataModule.setField(block.getBlockData(), field, request.bodyOrException()))
                .map(block -> Response.create().noContent());
    }
}

package com.github.jenya705.mcapi.server.module.rest.route.block;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.error.BlockNotFoundException;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.github.jenya705.mcapi.world.World;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Optional;

/**
 * @author Jenya705
 */
@Singleton
public class GetBlockRouteHandler extends AbstractRouteHandler {

    @Inject
    public GetBlockRouteHandler(ServerApplication application) {
        super(application, Routes.BLOCK);
    }

    @Override
    public void handle(Request request, Response response) throws Exception {
        World world = request.paramOrException("id", World.class);
        int x = request.paramOrException("x", int.class);
        int y = request.paramOrException("y", int.class);
        int z = request.paramOrException("z", int.class);
        request
                .bot()
                .needPermission(Permissions.BLOCK_GET);
        response.ok(
                Optional.ofNullable(
                        world.getBlock(x, y, z)
                ).orElseThrow(BlockNotFoundException::create)
        );
    }
}

package com.github.jenya705.mcapi.server.module.rest.route.world;

import com.github.jenya705.mcapi.NamespacedKey;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.error.WorldNotFoundException;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.github.jenya705.mcapi.server.util.ReactorUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import reactor.core.publisher.Mono;

/**
 * @author Jenya705
 */
@Singleton
public class GetWorldRouteHandler extends AbstractRouteHandler {

    @Inject
    public GetWorldRouteHandler(ServerApplication application) {
        super(application, Routes.WORLD);
    }

    @Override
    public Mono<Response> handle(Request request) {
        NamespacedKey worldId = request.paramOrException("id", NamespacedKey.class);
        request
                .bot()
                .needPermission(Permissions.WORLD_GET);
        return core()
                .getWorld(worldId)
                .flatMap(world -> ReactorUtils.ifNullError(
                        world, () -> WorldNotFoundException.create(worldId)))
                .map(world -> Response.create().ok(world));
    }
}

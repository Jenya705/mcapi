package com.github.jenya705.mcapi.server.module.rest.route.world;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.error.WorldNotFoundException;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.google.inject.Inject;
import com.google.inject.Singleton;

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
    public void handle(Request request, Response response) throws Exception {
        String id = request.paramOrException("id");
        request
                .bot()
                .needPermission(Permissions.WORLD_GET);
        response.ok(
                core()
                        .getOptionalWorld(id)
                        .orElseThrow(() -> WorldNotFoundException.create(id))
        );
    }
}

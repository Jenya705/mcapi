package com.github.jenya705.mcapi.module.rest.route.world;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.error.WorldNotFoundException;
import com.github.jenya705.mcapi.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.module.web.Response;
import com.github.jenya705.mcapi.permission.Permissions;

/**
 * @author Jenya705
 */
public class GetWorldRouteHandler extends AbstractRouteHandler {

    public GetWorldRouteHandler() {
        super(Routes.WORLD);
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
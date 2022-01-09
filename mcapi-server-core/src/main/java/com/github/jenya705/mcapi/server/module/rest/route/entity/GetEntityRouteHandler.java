package com.github.jenya705.mcapi.server.module.rest.route.entity;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.entity.Entity;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.github.jenya705.mcapi.server.util.PermissionUtils;

/**
 * @author Jenya705
 */
public class GetEntityRouteHandler extends AbstractRouteHandler {

    public GetEntityRouteHandler() {
        super(Routes.ENTITY);
    }

    @Override
    public void handle(Request request, Response response) throws Exception {
        Entity entity = request.paramOrException("id", Entity.class);
        request
                .bot()
                .needPermission(PermissionUtils.getEntity(entity));
        response.ok(entity);
    }
}

package com.github.jenya705.mcapi.server.module.rest.route.entity;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.entity.Entity;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.github.jenya705.mcapi.server.util.PermissionUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Jenya705
 */
@Singleton
public class GetEntityRouteHandler extends AbstractRouteHandler {

    @Inject
    public GetEntityRouteHandler(ServerApplication application) {
        super(application, Routes.ENTITY);
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

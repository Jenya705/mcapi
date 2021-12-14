package com.github.jenya705.mcapi.module.rest.route.entity;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.entity.Entity;
import com.github.jenya705.mcapi.error.EntityNotFoundException;
import com.github.jenya705.mcapi.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.module.web.Response;
import com.github.jenya705.mcapi.permission.Permissions;

import java.util.UUID;

/**
 * @author Jenya705
 */
public class GetEntityRouteHandler extends AbstractRouteHandler {

    public GetEntityRouteHandler() {
        super(Routes.ENTITY);
    }

    @Override
    public void handle(Request request, Response response) throws Exception {
        UUID uuid = request.paramOrException("id", UUID.class);
        Entity entity = core()
                .getOptionalEntity(uuid)
                .orElseThrow(() -> EntityNotFoundException.create(uuid));
        request
                .bot()
                .needPermission(Permissions.ENTITY_GET + "." + entity.getType());
        response.ok(entity);
    }
}

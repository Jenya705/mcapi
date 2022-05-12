package com.github.jenya705.mcapi.server.module.rest.route.entity;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.error.EntityNotFoundException;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.github.jenya705.mcapi.server.util.PermissionUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import reactor.core.publisher.Mono;

import java.util.UUID;

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
    public Mono<Response> handle(Request request) {
        UUID entityUuid = request.paramOrException("id", UUID.class);
        return core()
                .getEntity(entityUuid)
                .switchIfEmpty(Mono.error(() -> EntityNotFoundException.create(entityUuid)))
                .map(entity -> {
                    request.bot().needPermission(PermissionUtils.getEntity(entity));
                    return Response.create().ok(entity);
                });
    }
}

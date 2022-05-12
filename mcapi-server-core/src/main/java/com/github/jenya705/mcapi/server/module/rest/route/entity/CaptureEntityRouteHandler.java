package com.github.jenya705.mcapi.server.module.rest.route.entity;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.entity.CapturableEntity;
import com.github.jenya705.mcapi.error.EntityNotFoundException;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.module.entity.capture.EntityCaptureModule;
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
public class CaptureEntityRouteHandler extends AbstractRouteHandler {

    private final EntityCaptureModule entityCaptureModule;

    @Inject
    public CaptureEntityRouteHandler(ServerApplication application, EntityCaptureModule entityCaptureModule) {
        super(application, Routes.CAPTURE_ENTITY);
        this.entityCaptureModule = entityCaptureModule;
    }

    @Override
    public Mono<Response> handle(Request request) {
        UUID uuid = request.paramOrException("id", UUID.class);
        return core()
                .getCapturableEntity(uuid)
                .switchIfEmpty(Mono.error(() -> EntityNotFoundException.create(uuid)))
                .map(capturableEntity -> {
                    AbstractBot bot = request.bot();
                    bot.needPermission(PermissionUtils.captureEntity(capturableEntity));
                    entityCaptureModule.capture(capturableEntity, bot);
                    return Response.create().noContent();
                });
    }
}

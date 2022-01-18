package com.github.jenya705.mcapi.server.module.rest.route.entity;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.entity.CapturableEntity;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.module.entity.capture.EntityCaptureModule;
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
public class CaptureEntityRouteHandler extends AbstractRouteHandler {

    private final EntityCaptureModule entityCaptureModule;

    @Inject
    public CaptureEntityRouteHandler(ServerApplication application, EntityCaptureModule entityCaptureModule) {
        super(application, Routes.CAPTURE_ENTITY);
        this.entityCaptureModule = entityCaptureModule;
    }

    @Override
    public void handle(Request request, Response response) throws Exception {
        CapturableEntity capturableEntity = request
                .paramOrException("id", CapturableEntity.class);
        AbstractBot bot = request.bot();
        bot.needPermission(PermissionUtils.captureEntity(capturableEntity));
        entityCaptureModule.capture(capturableEntity, bot);
        response.noContent();
    }
}

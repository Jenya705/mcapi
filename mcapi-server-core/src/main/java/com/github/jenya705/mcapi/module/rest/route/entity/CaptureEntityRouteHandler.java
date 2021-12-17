package com.github.jenya705.mcapi.module.rest.route.entity;

import com.github.jenya705.mcapi.Bean;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.entity.CapturableEntity;
import com.github.jenya705.mcapi.module.entity.capture.EntityCaptureModule;
import com.github.jenya705.mcapi.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.module.web.Response;
import com.github.jenya705.mcapi.util.PermissionUtils;

/**
 * @author Jenya705
 */
public class CaptureEntityRouteHandler extends AbstractRouteHandler {

    @Bean
    private EntityCaptureModule entityCaptureModule;

    public CaptureEntityRouteHandler() {
        super(Routes.CAPTURE_ENTITY);
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

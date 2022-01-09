package com.github.jenya705.mcapi.server.module.entity.capture;

import com.github.jenya705.mcapi.entity.CapturableEntity;
import com.github.jenya705.mcapi.server.entity.AbstractBot;

import java.util.Collection;

/**
 * @author Jenya705
 */
public interface EntityCaptureModule {

    void capture(CapturableEntity entity, AbstractBot owner);

    Collection<? extends CapturableEntity> getCapturedEntities(AbstractBot owner);

}

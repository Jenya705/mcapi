package com.github.jenya705.mcapi.module.entity.capture;

import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.entity.CapturableEntity;

import java.util.Collection;

/**
 * @author Jenya705
 */
public interface EntityCaptureModule {

    void capture(CapturableEntity entity, AbstractBot owner);

    Collection<? extends CapturableEntity> getCapturedEntities(AbstractBot owner);

}

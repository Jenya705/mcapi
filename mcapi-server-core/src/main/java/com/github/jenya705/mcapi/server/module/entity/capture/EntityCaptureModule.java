package com.github.jenya705.mcapi.server.module.entity.capture;

import com.github.jenya705.mcapi.entity.CapturableEntity;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.google.inject.ImplementedBy;
import reactor.core.publisher.Flux;

import java.util.Collection;

/**
 * @author Jenya705
 */
@ImplementedBy(EntityCaptureModuleImpl.class)
public interface EntityCaptureModule {

    void capture(CapturableEntity entity, AbstractBot owner);

    Flux<? extends CapturableEntity> getCapturedEntities(AbstractBot owner);

}

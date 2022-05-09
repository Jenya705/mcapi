package com.github.jenya705.mcapi.server.module.entity.capture;

import com.github.jenya705.mcapi.entity.CapturableEntity;
import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@Singleton
public class EntityCaptureModuleImpl extends AbstractApplicationModule implements EntityCaptureModule {

    @Inject
    public EntityCaptureModuleImpl(ServerApplication application) {
        super(application);
    }

    @Override
    public void capture(CapturableEntity entity, AbstractBot owner) {
        entity.setOwner(owner.getEntity().getId());
    }

    @Override
    public Collection<? extends CapturableEntity> getCapturedEntities(AbstractBot owner) {
        return core()
                .getEntities()
                .filter(entity ->
                        entity instanceof CapturableEntity &&
                                ((CapturableEntity) entity).getOwner() == owner.getEntity().getId()
                )
                .map(it -> (CapturableEntity) it)
                .collect(Collectors.toList())
                .block();
    }

}

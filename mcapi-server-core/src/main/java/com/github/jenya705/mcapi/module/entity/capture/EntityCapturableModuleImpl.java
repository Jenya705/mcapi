package com.github.jenya705.mcapi.module.entity.capture;

import com.github.jenya705.mcapi.AbstractApplicationModule;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.entity.CapturableEntity;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
public class EntityCapturableModuleImpl extends AbstractApplicationModule implements EntityCaptureModule {

    @Override
    public void capture(CapturableEntity entity, AbstractBot owner) {
        entity.setOwner(owner.getEntity().getId());
    }

    @Override
    public Collection<? extends CapturableEntity> getCapturedEntities(AbstractBot owner) {
        return core()
                .getEntities(entity ->
                        entity instanceof CapturableEntity &&
                                ((CapturableEntity) entity).getOwner() == owner.getEntity().getId()
                )
                .stream()
                .map(it -> (CapturableEntity) it)
                .collect(Collectors.toList());
    }

}

package com.github.jenya705.mcapi.server.module.entity.capture;

import com.github.jenya705.mcapi.entity.CapturableEntity;
import com.github.jenya705.mcapi.entity.Entity;
import com.github.jenya705.mcapi.event.EntityDespawnEvent;
import com.github.jenya705.mcapi.event.EntitySpawnEvent;
import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.event.EventLoop;
import com.github.jenya705.mcapi.server.util.MultivaluedMap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import reactor.core.publisher.Flux;

import java.util.Collections;

/**
 * @author Jenya705
 */
@Singleton
public class EntityCaptureModuleImpl extends AbstractApplicationModule implements EntityCaptureModule {

    private final MultivaluedMap<Integer, CapturableEntity> entities = MultivaluedMap.concurrent();

    @Inject
    public EntityCaptureModuleImpl(ServerApplication application, EventLoop eventLoop, Logger logger) {
        super(application);
        eventLoop.asyncHandler(EntitySpawnEvent.class, e -> {
            Entity spawned = e.getSpawned();
            if (!(spawned instanceof CapturableEntity)) return;
            CapturableEntity capturableSpawned = (CapturableEntity) spawned;
            int owner = capturableSpawned.getOwner();
            if (owner == -1) return;
            entities.add(owner, capturableSpawned);
        });
        eventLoop.asyncHandler(EntityDespawnEvent.class, e -> {
            Entity despawned = e.getDespawned();
            if (!(despawned instanceof CapturableEntity)) return;
            CapturableEntity capturableDespawned = (CapturableEntity) despawned;
            int owner = capturableDespawned.getOwner();
            if (owner == -1) return;
            if (!entities.removeElement(owner, capturableDespawned)) {
                logger.warn("Someone changing owner without notifying the EntityCaptureModule.");
            }
        });
    }

    @Override
    public void capture(CapturableEntity entity, AbstractBot owner) {
        int oldOwner = entity.getOwner();
        entities.add(owner.getEntity().getId(), entity);
        entity.setOwner(owner.getEntity().getId());
        entities.removeElement(oldOwner, entity);
    }

    @Override
    public Flux<? extends CapturableEntity> getCapturedEntities(AbstractBot owner) {
        return Flux.fromIterable(entities
                .getOrDefault(owner.getEntity().getId(), Collections.emptyList())
        );
    }

}

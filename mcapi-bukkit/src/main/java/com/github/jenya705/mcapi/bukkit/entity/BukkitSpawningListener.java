package com.github.jenya705.mcapi.bukkit.entity;

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import com.github.jenya705.mcapi.bukkit.BukkitApplication;
import com.github.jenya705.mcapi.bukkit.wrapper.BukkitWrapper;
import com.github.jenya705.mcapi.entity.event.EntityEntityDespawnEvent;
import com.github.jenya705.mcapi.entity.event.EntityEntitySpawnEvent;
import com.github.jenya705.mcapi.server.event.EventLoop;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

/**
 * @author Jenya705
 */
@Singleton
public class BukkitSpawningListener implements Listener {

    private final EventLoop eventLoop;

    @Inject
    public BukkitSpawningListener(BukkitApplication application, EventLoop eventLoop) {
        this.eventLoop = eventLoop;
        application.getServer().getPluginManager().registerEvents(this, application);
    }

    @EventHandler
    public void spawn(EntitySpawnEvent event) {
        eventLoop.invoke(new EntityEntitySpawnEvent(
                BukkitWrapper.entity(event.getEntity())
        ));
    }

    @EventHandler
    public void despawn(EntityRemoveFromWorldEvent event) {
        eventLoop.invoke(new EntityEntityDespawnEvent(
                BukkitWrapper.entity(event.getEntity())
        ));
    }

}

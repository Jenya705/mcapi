package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.BoundingBox;
import com.github.jenya705.mcapi.BukkitWrapper;
import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.Vector3;
import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;

import java.util.UUID;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class BukkitEntityWrapper implements Entity {

    private final org.bukkit.entity.Entity entity;

    public static BukkitEntityWrapper of(org.bukkit.entity.Entity entity) {
        if (entity == null) return null;
        return new BukkitEntityWrapper(entity);
    }

    @Override
    public String getType() {
        return entity.getType().getKey().toString();
    }

    @Override
    public UUID getUuid() {
        return entity.getUniqueId();
    }

    @Override
    public Location getLocation() {
        return BukkitWrapper.location(entity.getLocation());
    }

    @Override
    public float getYaw() {
        return entity.getLocation().getYaw();
    }

    @Override
    public float getPitch() {
        return entity.getLocation().getPitch();
    }

    @Override
    public Vector3 getVelocity() {
        return BukkitWrapper.vector(entity.getVelocity());
    }

    @Override
    public BoundingBox getBoundingBox() {
        return BukkitWrapper.boundingBox(entity.getBoundingBox());
    }

    @Override
    public int getFireTicks() {
        return entity.getFireTicks();
    }

    @Override
    public Component customName() {
        return entity.customName();
    }

    @Override
    public boolean isCustomNameVisible() {
        return entity.isCustomNameVisible();
    }

    @Override
    public boolean isSilent() {
        return entity.isSilent();
    }
}

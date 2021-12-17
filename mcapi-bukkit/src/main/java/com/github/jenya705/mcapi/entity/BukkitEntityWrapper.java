package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.*;
import lombok.experimental.Delegate;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

/**
 * @author Jenya705
 */
public class BukkitEntityWrapper implements CapturableEntity {

    public static final NamespacedKey ownerKey = new NamespacedKey(BukkitUtils.getPlugin(), "owner");

    private final org.bukkit.entity.Entity entity;

    public BukkitEntityWrapper(org.bukkit.entity.Entity entity) {
        this.entity = entity;
    }

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

    @Override
    public void setOwner(int id) {
        entity
                .getPersistentDataContainer()
                .set(ownerKey, PersistentDataType.INTEGER, id);
    }

    @Override
    public int getOwner() {
        return entity
                .getPersistentDataContainer()
                .getOrDefault(ownerKey, PersistentDataType.INTEGER, -1);
    }

}

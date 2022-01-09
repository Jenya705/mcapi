package com.github.jenya705.mcapi.bukkit.entity;

import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.bukkit.BukkitUtils;
import com.github.jenya705.mcapi.bukkit.BukkitWrapper;
import com.github.jenya705.mcapi.entity.CapturableEntity;
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
    public Component customName() {
        return entity.customName();
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

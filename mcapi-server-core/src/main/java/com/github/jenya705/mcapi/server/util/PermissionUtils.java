package com.github.jenya705.mcapi.server.util;

import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.entity.Entity;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.world.World;
import lombok.experimental.UtilityClass;

/**
 * @author Jenya705
 */
@UtilityClass
public class PermissionUtils {

    public String getData(String material) {
        return Permissions.BLOCK_DATA + "." + material;
    }

    public String getData(Block block) {
        return getData(block.getMaterial().getKey());
    }

    public String updateData(String material) {
        return Permissions.BLOCK_DATA_FIELD + "." + material;
    }

    public String updateData(Block block) {
        return updateData(block.getMaterial().getKey());
    }

    public String getEntity(String entityType) {
        return Permissions.ENTITY_GET + "." + entityType;
    }

    public String getEntity(Entity entity) {
        return getEntity(entity.getType());
    }

    public String captureEntity(String entityType) {
        return Permissions.CAPTURE_ENTITY + "." + entityType;
    }

    public String captureEntity(Entity entity) {
        return captureEntity(entity.getType());
    }

    public String worldPermission(String permission, World world) {
        return permission + "." + world.getName();
    }

    public String blockPermission(String permission, Block block) {
        return worldPermission(permission, block.getLocation().getWorld());
    }

}

package com.github.jenya705.mcapi.block;

import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.Material;
import com.github.jenya705.mcapi.UUIDHolder;
import com.github.jenya705.mcapi.Vector3;

import java.util.UUID;

/**
 * @author Jenya705
 */
public interface Block extends UUIDHolder {

    long zMask = 0xffffffff;
    long xMask = zMask << 32;
    long yMask = zMask;

    static UUID getUuid(Vector3 pos) {
        long x = (long) pos.getX();
        long y = (long) pos.getY();
        long z = (long) pos.getZ();
        return new UUID((x << 32) | z, y);
    }

    static Vector3 fromUuid(UUID uuid) {
        return Vector3.of(
                (uuid.getMostSignificantBits() & xMask) >> 32,
                uuid.getLeastSignificantBits() & yMask,
                uuid.getMostSignificantBits() & zMask
        );
    }

    Location getLocation();

    Material getMaterial();

    BlockData getBlockData();

    default UUID getUuid() {
        return getUuid(getLocation().asVector());
    }

}

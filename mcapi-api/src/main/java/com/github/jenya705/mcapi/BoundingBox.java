package com.github.jenya705.mcapi;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author Jenya705
 */
@Getter
@EqualsAndHashCode
@ToString(includeFieldNames = false)
public final class BoundingBox {

    private final Vector3 minCorner;
    private final Vector3 maxCorner;

    public BoundingBox(Vector3 corner1, Vector3 corner2) {
        double minX = Math.min(corner1.getX(), corner2.getX());
        double minY = Math.min(corner1.getY(), corner2.getY());
        double minZ = Math.min(corner1.getZ(), corner2.getZ());
        double maxX = Math.max(corner1.getX(), corner2.getX());
        double maxY = Math.max(corner1.getY(), corner2.getY());
        double maxZ = Math.max(corner1.getZ(), corner2.getZ());

        minCorner = Vector3.of(minX, minY, minZ);
        maxCorner = Vector3.of(maxX, maxY, maxZ);
    }

    public BoundingBox minCorner(Vector3 minCorner) {
        return new BoundingBox(minCorner, maxCorner);
    }

    public BoundingBox maxCorner(Vector3 maxCorner) {
        return new BoundingBox(minCorner, maxCorner);
    }

    public BoundingBox minCorner(Function<Vector3, Vector3> function) {
        return minCorner(function.apply(minCorner));
    }

    public BoundingBox maxCorner(Function<Vector3, Vector3> function) {
        return maxCorner(function.apply(maxCorner));
    }

    public double getWidthX() {
        return maxCorner.getX() - minCorner.getX();
    }

    public double getWidthZ() {
        return maxCorner.getZ() - minCorner.getZ();
    }

    public double getHeight() {
        return maxCorner.getY() - minCorner.getY();
    }

    public double getVolume() {
        return getWidthX() * getWidthZ() * getHeight();
    }

    public Vector3 size() {
        return Vector3.of(
                getWidthX(),
                getHeight(),
                getWidthZ()
        );
    }

}

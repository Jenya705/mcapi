package com.github.jenya705.mcapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Jenya705
 */
@Getter
@ToString(includeFieldNames = false)
@AllArgsConstructor
public final class Vector3 {

    public static final double epsilon = 1e-4;

    private final double x;
    private final double y;
    private final double z;

    public Vector3() {
        this(0, 0, 0);
    }

    public static Vector3 of(double x, double y, double z) {
        return new Vector3(x, y, z);
    }

    public static Vector3 zero() {
        return new Vector3();
    }

    public Vector3 add(Vector3 other) {
        return new Vector3(
                x + other.x,
                y + other.y,
                z + other.z
        );
    }

    public Vector3 subtract(Vector3 other) {
        return new Vector3(
                x - other.x,
                y - other.y,
                z - other.z
        );
    }

    public Vector3 multiply(Vector3 other) {
        return new Vector3(
                x * other.x,
                y * other.y,
                z * other.z
        );
    }

    public Vector3 divide(Vector3 other) {
        return new Vector3(
                x / other.x,
                y / other.y,
                z / other.z
        );
    }

    public Vector3 add(double m) {
        return new Vector3(
                x + m,
                y + m,
                z + m
        );
    }

    public Vector3 subtract(double m) {
        return new Vector3(
                x - m,
                y - m,
                z - m
        );
    }

    public Vector3 multiply(double m) {
        return new Vector3(
                x * m,
                y * m,
                z * m
        );
    }

    public Vector3 divide(double m) {
        return new Vector3(
                x / m,
                y / m,
                z / m
        );
    }

    public Vector3 withX(double x) {
        return new Vector3(x, y, z);
    }

    public Vector3 withY(double y) {
        return new Vector3(x, y, z);
    }

    public Vector3 withZ(double z) {
        return new Vector3(x, y, z);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Vector3)) return false;
        Vector3 other = (Vector3) obj;
        return Math.abs(x - other.x) < epsilon &&
                Math.abs(y - other.y) < epsilon &&
                Math.abs(z - other.z) < epsilon;
    }
}

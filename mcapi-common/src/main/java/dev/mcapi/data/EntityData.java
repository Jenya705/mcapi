package dev.mcapi.data;

import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
public interface EntityData {

    /**
     * Returns unique id of this entity
     *
     * @return Unique id
     */
    UUID uniqueId();

    /**
     * Returns health of this entity. Possible it is rounded to reach some minimal value
     *
     * @return Health
     */
    float health();

    /**
     * Returns position of this entity
     *
     * @return Position
     */
    Vector3D position();

}

package com.github.jenya705.mcapi.entity;

/**
 * @author Jenya705
 */
public interface LivingEntity extends Entity {

    float getHealth();

    boolean isSwimming();

    boolean hasAI();

    boolean isColliding();

}

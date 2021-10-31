package com.github.jenya705.mcapi;

/**
 * @author Jenya705
 */
public interface Material {

    boolean isBlock();

    boolean isItem();

    boolean isEdible();

    boolean hasGravity();

    boolean isBurnable();

    boolean isFuel();

    String getKey();

}

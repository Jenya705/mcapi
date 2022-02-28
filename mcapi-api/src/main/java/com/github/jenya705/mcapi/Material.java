package com.github.jenya705.mcapi;

import org.jetbrains.annotations.NotNull;

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

    @NotNull NamespacedKey getKey();

}

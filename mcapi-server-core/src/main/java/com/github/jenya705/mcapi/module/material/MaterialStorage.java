package com.github.jenya705.mcapi.module.material;

import com.github.jenya705.mcapi.Material;

/**
 * @author Jenya705
 */
public interface MaterialStorage {

    Material getMaterial(String key);

    void addMaterial(Material material);

}

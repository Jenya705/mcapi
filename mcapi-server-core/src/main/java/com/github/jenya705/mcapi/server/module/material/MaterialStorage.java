package com.github.jenya705.mcapi.server.module.material;

import com.github.jenya705.mcapi.Material;

/**
 * @author Jenya705
 */
public interface MaterialStorage {

    Material getMaterial(String key);

    void addMaterial(Material material);

}

package com.github.jenya705.mcapi.server.module.material;

import com.github.jenya705.mcapi.Material;
import com.google.inject.ImplementedBy;

/**
 * @author Jenya705
 */
@ImplementedBy(MaterialStorageImpl.class)
public interface MaterialStorage {

    Material getMaterial(String key);

    void addMaterial(Material material);

}

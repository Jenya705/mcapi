package com.github.jenya705.mcapi.module.material;

import com.github.jenya705.mcapi.Material;
import com.github.jenya705.mcapi.VanillaMaterial;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jenya705
 */
public class MaterialStorageImpl implements MaterialStorage {

    private final Map<String, Material> materials = new HashMap<>();

    public MaterialStorageImpl() {
        for (Material material: VanillaMaterial.values()) {
            addMaterial(material);
        }
    }

    @Override
    public Material getMaterial(String key) {
        return materials.get(key.toLowerCase(Locale.ROOT));
    }

    @Override
    public void addMaterial(Material material) {
        materials.put(material.getKey().toLowerCase(Locale.ROOT), material);
    }
}

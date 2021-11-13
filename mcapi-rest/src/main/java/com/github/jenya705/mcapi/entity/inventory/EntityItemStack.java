package com.github.jenya705.mcapi.entity.inventory;

import com.github.jenya705.mcapi.Material;
import com.github.jenya705.mcapi.inventory.ItemStack;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityItemStack implements ItemStack {

    private Material material;
    private int amount;
    private Component customName;

    public String getCustomName() {
        return customName == null ? null :
                LegacyComponentSerializer
                        .legacyAmpersand()
                        .serialize(customName);
    }

    @Override
    public Component customName() {
        return customName;
    }
}

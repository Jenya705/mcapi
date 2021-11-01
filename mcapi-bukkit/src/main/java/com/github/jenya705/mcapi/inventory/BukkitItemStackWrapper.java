package com.github.jenya705.mcapi.inventory;

import com.github.jenya705.mcapi.Material;
import com.github.jenya705.mcapi.VanillaMaterial;
import lombok.AllArgsConstructor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class BukkitItemStackWrapper implements ItemStack {

    private final org.bukkit.inventory.ItemStack itemStack;

    public static BukkitItemStackWrapper of(org.bukkit.inventory.ItemStack itemStack) {
        if (itemStack == null) return null;
        return new BukkitItemStackWrapper(itemStack);
    }

    @Override
    public Material getMaterial() {
        return VanillaMaterial.getMaterial(itemStack.getType().getKey().getKey());
    }

    @Override
    public int getAmount() {
        return itemStack.getAmount();
    }

    @Override
    public String getCustomName() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.hasDisplayName()) {
            return LegacyComponentSerializer
                    .legacyAmpersand()
                    .serialize(itemStack.displayName());
        }
        return null;
    }
}

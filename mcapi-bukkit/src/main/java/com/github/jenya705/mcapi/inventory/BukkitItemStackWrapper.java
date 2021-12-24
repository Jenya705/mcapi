package com.github.jenya705.mcapi.inventory;

import com.github.jenya705.mcapi.BukkitWrapper;
import com.github.jenya705.mcapi.Material;
import com.github.jenya705.mcapi.VanillaMaterial;
import com.github.jenya705.mcapi.enchantment.ItemEnchantment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@RequiredArgsConstructor
public class BukkitItemStackWrapper implements ItemStack {

    @Getter
    private final org.bukkit.inventory.ItemStack itemStack;

    private List<ItemEnchantment> enchantments;

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
        if (itemMeta != null && itemMeta.hasDisplayName()) {
            Component displayName = itemMeta.displayName();
            if (displayName == null) return null;
            return LegacyComponentSerializer
                    .legacyAmpersand()
                    .serialize(displayName);
        }
        return null;
    }

    @Override
    public Component customName() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null && itemMeta.hasDisplayName()) {
            return itemMeta.displayName();
        }
        return null;
    }

    @Override
    public List<ItemEnchantment> getEnchantments() {
        if (enchantments == null) {
            enchantments = itemStack
                    .getEnchantments()
                    .entrySet()
                    .stream()
                    .map(it -> BukkitWrapper.itemEnchantment(it.getKey(), it.getValue()))
                    .collect(Collectors.toList());
        }
        return enchantments;
    }
}

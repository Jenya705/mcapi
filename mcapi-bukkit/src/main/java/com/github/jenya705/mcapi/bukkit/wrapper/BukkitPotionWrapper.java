package com.github.jenya705.mcapi.bukkit.wrapper;

import com.github.jenya705.mcapi.potion.PotionEffectType;
import com.github.jenya705.mcapi.potion.VanillaPotionEffectType;
import com.github.jenya705.mcapi.server.util.Pair;
import lombok.experimental.UtilityClass;

import java.util.List;

/**
 * @author Jenya705
 */
@UtilityClass
public class BukkitPotionWrapper {

    private final List<Pair<VanillaPotionEffectType, org.bukkit.potion.PotionEffectType>> overridePotionEffectTypes = List.of(
            new Pair<>(VanillaPotionEffectType.MINING_FATIGUE, org.bukkit.potion.PotionEffectType.SLOW_DIGGING),
            new Pair<>(VanillaPotionEffectType.INSTANT_HEALTH, org.bukkit.potion.PotionEffectType.HEAL),
            new Pair<>(VanillaPotionEffectType.INSTANT_DAMAGE, org.bukkit.potion.PotionEffectType.HARM),
            new Pair<>(VanillaPotionEffectType.HASTE, org.bukkit.potion.PotionEffectType.FAST_DIGGING),
            new Pair<>(VanillaPotionEffectType.STRENGTH, org.bukkit.potion.PotionEffectType.INCREASE_DAMAGE),
            new Pair<>(VanillaPotionEffectType.SLOWNESS, org.bukkit.potion.PotionEffectType.SLOW),
            new Pair<>(VanillaPotionEffectType.JUMP_BOOST, org.bukkit.potion.PotionEffectType.JUMP),
            new Pair<>(VanillaPotionEffectType.NAUSEA, org.bukkit.potion.PotionEffectType.CONDUIT_POWER),
            new Pair<>(VanillaPotionEffectType.RESISTANCE, org.bukkit.potion.PotionEffectType.DAMAGE_RESISTANCE)
    );

    public PotionEffectType potionEffectType(org.bukkit.potion.PotionEffectType effectType) {
        for (var overridePotionEffectType: overridePotionEffectTypes) {
            if (overridePotionEffectType.getRight().equals(effectType)) {
                return overridePotionEffectType.getLeft();
            }
        }
        return VanillaPotionEffectType.valueOf(effectType.getName());
    }

    public org.bukkit.potion.PotionEffectType potionEffectType(PotionEffectType effectType) {
        if (!effectType.getKey().getDomain().equals("minecraft")) return null;
        for (var overridePotionEffectType: overridePotionEffectTypes) {
            if (overridePotionEffectType.getLeft().equals(effectType)) {
                return overridePotionEffectType.getRight();
            }
        }
        return org.bukkit.potion.PotionEffectType.getByName(effectType.getKey().getKey());
    }

}

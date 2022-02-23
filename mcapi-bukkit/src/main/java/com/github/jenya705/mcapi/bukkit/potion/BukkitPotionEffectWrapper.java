package com.github.jenya705.mcapi.bukkit.potion;

import com.github.jenya705.mcapi.bukkit.wrapper.BukkitWrapper;
import com.github.jenya705.mcapi.potion.PotionEffect;
import com.github.jenya705.mcapi.potion.PotionEffectType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Jenya705
 */
@RequiredArgsConstructor
public class BukkitPotionEffectWrapper implements PotionEffect {

    @Getter
    private final org.bukkit.potion.PotionEffect bukkitPotionEffect;

    public static BukkitPotionEffectWrapper of(org.bukkit.potion.PotionEffect potionEffect) {
        return potionEffect == null ? null : new BukkitPotionEffectWrapper(potionEffect);
    }

    @Override
    public PotionEffectType getType() {
        return BukkitWrapper.potionEffectType(bukkitPotionEffect.getType());
    }

    @Override
    public int getAmplifier() {
        return bukkitPotionEffect.getAmplifier();
    }

    @Override
    public long getDuration() {
        return bukkitPotionEffect.getDuration() * 50L;
    }

    @Override
    public boolean isAmbient() {
        return bukkitPotionEffect.isAmbient();
    }

    @Override
    public boolean isHidden() {
        return !bukkitPotionEffect.hasIcon() && !bukkitPotionEffect.hasParticles();
    }
}

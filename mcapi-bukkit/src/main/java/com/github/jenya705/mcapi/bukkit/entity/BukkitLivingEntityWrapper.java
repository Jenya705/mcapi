package com.github.jenya705.mcapi.bukkit.entity;

import com.github.jenya705.mcapi.bukkit.BukkitUtils;
import com.github.jenya705.mcapi.bukkit.wrapper.BukkitWrapper;
import com.github.jenya705.mcapi.entity.Entity;
import com.github.jenya705.mcapi.entity.LivingEntity;
import com.github.jenya705.mcapi.potion.PotionEffect;
import lombok.experimental.Delegate;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
public class BukkitLivingEntityWrapper implements LivingEntity {

    @Delegate
    private final Entity entityDelegate;

    private final org.bukkit.entity.LivingEntity livingEntity;

    public BukkitLivingEntityWrapper(org.bukkit.entity.LivingEntity livingEntity) {
        this.livingEntity = livingEntity;
        entityDelegate = new BukkitEntityWrapper(livingEntity);
    }

    public static BukkitLivingEntityWrapper of(org.bukkit.entity.LivingEntity livingEntity) {
        if (livingEntity == null) return null;
        return new BukkitLivingEntityWrapper(livingEntity);
    }

    @Override
    public Collection<PotionEffect> getEffects() {
        return livingEntity
                .getActivePotionEffects()
                .stream()
                .map(BukkitWrapper::potionEffect)
                .collect(Collectors.toList());
    }

    @Override
    public float getHealth() {
        return (float) livingEntity.getHealth();
    }

    @Override
    public boolean hasAI() {
        return livingEntity.hasAI();
    }

    @Override
    public void kill() {
        BukkitUtils.notAsyncTask(() -> livingEntity.setHealth(0));
    }
}

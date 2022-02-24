package com.github.jenya705.mcapi.bukkit.block.data;

import com.github.jenya705.mcapi.block.data.Beacon;
import com.github.jenya705.mcapi.bukkit.block.AbstractBukkitBlockState;
import com.github.jenya705.mcapi.bukkit.wrapper.BukkitWrapper;
import com.github.jenya705.mcapi.entity.LivingEntity;
import com.github.jenya705.mcapi.potion.PotionEffect;
import com.github.jenya705.mcapi.potion.PotionEffectType;
import org.bukkit.block.Block;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
public class BukkitBeaconWrapper extends AbstractBukkitBlockState<org.bukkit.block.Beacon> implements Beacon {

    private Collection<LivingEntity> affectedEntities;

    public BukkitBeaconWrapper(Block block) {
        super(block);
    }

    @Override
    public Collection<LivingEntity> getAffectedEntities() {
         if (affectedEntities == null) {
             affectedEntities = state()
                     .getEntitiesInRange()
                     .stream()
                     .map(BukkitWrapper::livingEntity)
                     .collect(Collectors.toList());
         }
         return affectedEntities;
    }

    @Override
    public PotionEffectType getPrimaryEffect() {
        return state().getPrimaryEffect() == null ?
                null : BukkitWrapper.potionEffectType(state().getPrimaryEffect().getType());
    }

    @Override
    public PotionEffectType getSecondaryEffect() {
        return state().getSecondaryEffect() == null ?
                null : BukkitWrapper.potionEffectType(state().getSecondaryEffect().getType());
    }

    @Override
    public int getTier() {
        return state().getTier();
    }

    @Override
    public void setPrimaryEffect(PotionEffectType type) {
        updateState(beacon -> beacon.setPrimaryEffect(BukkitWrapper.potionEffectType(type)));
    }

    @Override
    public void setSecondaryEffect(PotionEffectType type) {
        updateState(beacon -> beacon.setSecondaryEffect(BukkitWrapper.potionEffectType(type)));
    }
}

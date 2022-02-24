package com.github.jenya705.mcapi.potion;

import lombok.Getter;

/**
 * @author Jenya705
 */
@Getter
public enum VanillaPotionEffectType implements PotionEffectType {

    MINING_FATIGUE("mining_fatigue", false),
    ABSORPTION("absorption", false),
    BAD_OMEN("bad_omen", false),
    BLINDNESS("blindness", false),
    CONDUIT_POWER("conduit_power", false),
    DOLPHINS_GRACE("dolphins_grace", false),
    FIRE_RESISTANCE("fire_resistance", false),
    GLOWING("glowing", false),
    HASTE("haste", false),
    HEALTH_BOOST("health_boost", false),
    HERO_OF_THE_VILLAGE("hero_of_the_village", false),
    HUNGER("hunger", false),
    INSTANT_DAMAGE("instant_damage", true),
    INSTANT_HEALTH("instant_health", true),
    INVISIBILITY("invisibility", false),
    JUMP_BOOST("jump_boost", false),
    LEVITATION("levitation", false),
    LUCK("luck", false),
    NAUSEA("nausea", false),
    NIGHT_VISION("night_vision", false),
    POISON("poison", false),
    REGENERATION("regeneration", false),
    RESISTANCE("resistance", false),
    SATURATION("saturation", true),
    SLOW_FALLING("slow_falling", false),
    SPEED("speed", false),
    SLOWNESS("slowness", false),
    STRENGTH("strength", false),
    UNLUCK("unluck", false),
    WATER_BREATHING("water_breathing", false),
    WEAKNESS("weakness", false),
    WITHER("wither", false);

    private final String key;
    private final boolean instant;

    VanillaPotionEffectType(String key, boolean instant) {
        this.key = "minecraft:" + key;
        this.instant = instant;
    }

}

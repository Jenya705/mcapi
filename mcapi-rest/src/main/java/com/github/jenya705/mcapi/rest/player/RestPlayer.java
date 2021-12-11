package com.github.jenya705.mcapi.rest.player;

import com.github.jenya705.mcapi.Vector3;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.rest.RestLocation;
import com.github.jenya705.mcapi.rest.RestShortBoundingBox;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;

import java.util.UUID;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestPlayer {

    private String name;
    private UUID uuid;
    private String type;
    private RestLocation location;
    private Vector3 velocity;
    private RestShortBoundingBox boundingBox;
    private float yaw;
    private float pitch;
    private String gameMode;
    private RestPlayerAbilities abilities;
    private float absorptionAmount;
    private float airLeft;
    private float fallDistance;
    private boolean crouching;
    private boolean sprinting;
    private boolean swimming;
    private boolean flyingWithElytra;
    private Component customName;
    private boolean customNameVisible;
    private boolean silent;
    private boolean falling;
    private int fireTicks;
    private int foodLevel;
    private float foodExhaustionLevel;
    private float foodSaturationLevel;
    private float health;
    private RestLocation spawn;
    private int xpLevel;
    private int xpPercentage;

    public static RestPlayer from(Player player) {
        return new RestPlayer(
                player.getName(),
                player.getUuid(),
                player.getType(),
                RestLocation.from(player.getLocation()),
                player.getVelocity(),
                RestShortBoundingBox.from(player.getBoundingBox()),
                player.getYaw(),
                player.getPitch(),
                player.getGameMode().name(),
                RestPlayerAbilities.from(player.getAbilities()),
                player.getAbsorptionAmount(),
                player.getAirLeft(),
                player.getFallDistance(),
                player.isCrouching(),
                player.isSprinting(),
                player.isSwimming(),
                player.isFlyingWithElytra(),
                player.customName(),
                player.isCustomNameVisible(),
                player.isSilent(),
                player.isFalling(),
                player.getFireTicks(),
                player.getFoodLevel(),
                player.getFoodExhaustionLevel(),
                player.getFoodSaturationLevel(),
                player.getHealth(),
                RestLocation.from(player.getSpawn()),
                player.getXpLevel(),
                player.getXpPercentage()
        );
    }

}

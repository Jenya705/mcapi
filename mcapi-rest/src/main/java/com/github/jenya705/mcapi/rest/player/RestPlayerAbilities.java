package com.github.jenya705.mcapi.rest.player;

import com.github.jenya705.mcapi.player.PlayerAbilities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestPlayerAbilities {

    private boolean invulnerable;
    private boolean mayFly;
    private boolean instabuild;
    private float walkSpeed;
    private boolean mayBuild;
    private boolean flying;
    private float flySpeed;

    public static RestPlayerAbilities from(PlayerAbilities abilities) {
        return new RestPlayerAbilities(
                abilities.isInvulnerable(),
                abilities.mayFly(),
                abilities.isInstabuild(),
                abilities.getWalkSpeed(),
                abilities.mayBuild(),
                abilities.isFlying(),
                abilities.getFlySpeed()
        );
    }

}

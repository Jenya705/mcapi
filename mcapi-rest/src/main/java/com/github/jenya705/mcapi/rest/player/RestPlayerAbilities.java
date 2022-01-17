package com.github.jenya705.mcapi.rest.player;

import com.github.jenya705.mcapi.jackson.DefaultBoolean;
import com.github.jenya705.mcapi.jackson.DefaultFloat;
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

    @DefaultBoolean(false)
    private boolean invulnerable;
    @DefaultBoolean(false)
    private boolean mayFly;
    @DefaultBoolean(false)
    private boolean instabuild;
    @DefaultFloat(0.1f)
    private float walkSpeed;
    @DefaultBoolean(true)
    private boolean mayBuild;
    @DefaultBoolean(false)
    private boolean flying;
    @DefaultFloat(0.05f)
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

package com.github.jenya705.mcapi.entity.player;

import com.github.jenya705.mcapi.player.PlayerAbilities;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Jenya705
 */
@Data
@AllArgsConstructor
public class EntityPlayerAbilities implements PlayerAbilities {

    private boolean invulnerable;
    private boolean mayFly;
    private boolean instabuild;
    private float walkSpeed;
    private boolean mayBuild;
    private boolean flying;
    private float flySpeed;

    @Override
    public boolean mayFly() {
        return mayFly;
    }

    @Override
    public boolean mayBuild() {
        return mayBuild;
    }

}

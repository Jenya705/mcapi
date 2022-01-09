package com.github.jenya705.mcapi.server.module.rest;

import com.github.jenya705.mcapi.player.GameMode;
import com.github.jenya705.mcapi.rest.entity.RestEntity;
import com.github.jenya705.mcapi.rest.player.RestPlayer;
import com.github.jenya705.mcapi.rest.player.RestPlayerAbilities;
import com.github.jenya705.mcapi.server.defaults.DefaultValue;
import lombok.experimental.UtilityClass;

/**
 * @author Jenya705
 */
@UtilityClass
public class RestDefaults {

    public final DefaultValue<RestPlayerAbilities> playerAbilities = DefaultValue.of(
            new RestPlayerAbilities(
                    false,
                    false,
                    false,
                    0.1f,
                    true,
                    false,
                    0.05f
            )
    );

    public final DefaultValue<RestPlayer> player = DefaultValue.of(
            new RestPlayer(
                    null,
                    null,
                    null, // default is minecraft:player but type is necessary for entities
                    null,
                    GameMode.SURVIVAL.name(),
                    playerAbilities.getDefaultValue(),
                    null,
                    20,
                    false
            )
    );

    public final DefaultValue<RestEntity> entity = DefaultValue.of(
            new RestEntity(
                    null,
                    null,
                    null
            )
    );

}

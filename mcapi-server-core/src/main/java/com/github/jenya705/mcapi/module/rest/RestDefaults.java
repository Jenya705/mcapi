package com.github.jenya705.mcapi.module.rest;

import com.github.jenya705.mcapi.Vector3;
import com.github.jenya705.mcapi.defaults.DefaultValue;
import com.github.jenya705.mcapi.player.GameMode;
import com.github.jenya705.mcapi.rest.RestShortBoundingBox;
import com.github.jenya705.mcapi.rest.entity.RestEntity;
import com.github.jenya705.mcapi.rest.player.RestPlayer;
import com.github.jenya705.mcapi.rest.player.RestPlayerAbilities;
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
                    Vector3.zero(),
                    new RestShortBoundingBox(0.6, 0.6, 1.8),
                    0,
                    0,
                    GameMode.SURVIVAL.name(),
                    playerAbilities.getDefaultValue(),
                    0,
                    300,
                    0,
                    false,
                    false,
                    false,
                    false,
                    null,
                    false,
                    false,
                    false,
                    -20,
                    20,
                    0,
                    20,
                    20,
                    null,
                    0,
                    0
            )
    );

    public final DefaultValue<RestEntity> entity = DefaultValue.of(
            new RestEntity(
                    null,
                    null,
                    0,
                    0,
                    Vector3.zero(),
                    new RestShortBoundingBox(0, 0, 0),
                    0,
                    null,
                    false,
                    false
            )
    );

}

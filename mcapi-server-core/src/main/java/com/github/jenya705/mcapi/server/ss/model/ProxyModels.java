package com.github.jenya705.mcapi.server.ss.model;

import com.github.jenya705.mcapi.player.PlayerID;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * @author Jenya705
 */
@UtilityClass
public class ProxyModels {

    public final Class<?> GET_PLAYER = PlayerID.class;

    public final Class<?> GET_ENTITY = UUID.class;

    public void registerModels(ProxyModelMapper modelMapper) throws Exception {
        for (Field field: ProxyModels.class.getFields()) {
            if (field.getType() != Class.class) continue;
            modelMapper.addModel(field.getName(), (Class<?>) field.get(null));
        }
    }

}

package com.github.jenya705.mcapi.server.ss.model;

import com.github.jenya705.mcapi.NamespacedKey;
import com.github.jenya705.mcapi.player.PlayerID;
import com.github.jenya705.mcapi.server.module.message.TypedMessage;
import lombok.experimental.UtilityClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.UUID;

/**
 * @author Jenya705
 */
@UtilityClass
public class ProxyModels {

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface ModelClass {
        Class<?> value();
    }

    @ModelClass(String.class)
    public final String AUTHENTICATION = "authentication";

    @ModelClass(PlayerID.class)
    public final String GET_PLAYER = "get_player";

    @ModelClass(NamespacedKey.class)
    public final String GET_WORLD = "get_world";

    @ModelClass(UUID.class)
    public final String GET_ENTITY = "get_entity";

    @ModelClass(TypedMessage.class)
    public final String SEND_MESSAGE = "send_message";

    @ModelClass(String.class)
    public final String KICK = "kick";

    @ModelClass(ModelEmpty.class)
    public final String KILL = "kill";

    @ModelClass(String.class)
    public final String CHAT = "chat";

    @ModelClass(String.class)
    public final String RUN_COMMAND = "run_command";

    public void registerModels(ProxyModelMapper modelMapper) throws Exception {
        for (Field field: ProxyModels.class.getFields()) {
            ModelClass modelClassAnnotation = field.getAnnotation(ModelClass.class);
            if (modelClassAnnotation == null || field.getType() != String.class) continue;
            modelMapper.addModel(field.get(null).toString(), modelClassAnnotation.value());
        }
    }

}

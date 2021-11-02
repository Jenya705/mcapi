package com.github.jenya705.mcapi.app;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

/**
 * @author Jenya705
 */
@UtilityClass
public class DefaultSerializers {

    public void registerAll(LibraryApplication<?, ?> application) {
        application.addSerializer(
                Component.class,
                (value, gen, serializers) ->
                    gen.writeRawValue(
                            GsonComponentSerializer.gson().serialize(value)
                    )
        );
    }
}

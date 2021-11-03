package com.github.jenya705.mcapi;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

import java.io.IOException;

/**
 * @author Jenya705
 */
public class ComponentJsonSerializer extends StdSerializer<Component> {

    public ComponentJsonSerializer() {
        super(Component.class);
    }

    @Override
    public void serialize(Component value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value == null) gen.writeRawValue("null");
        else gen.writeRawValue(GsonComponentSerializer.gson().serialize(value));
    }
}

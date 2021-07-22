package com.github.jenya705.mcapi.jackson;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

import java.io.IOException;

/**
 * @author Jenya705
 */
public class JavaServerComponentParser {

    protected static final GsonComponentSerializer componentSerializer = GsonComponentSerializer.gson();

    public static class Serializer extends StdSerializer<Component> {

        public Serializer() {
            super(Component.class);
        }

        @Override
        public void serialize(Component value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeString(componentSerializer.serialize(value));
        }
    }

    public static class Deserializer extends StdDeserializer<Component> {

        public Deserializer() {
            super(Component.class);
        }

        @Override
        public Component deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            JsonNode node = p.getCodec().readTree(p);
            return componentSerializer.deserialize(node.toString());
        }
    }

}

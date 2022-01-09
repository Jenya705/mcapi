package com.github.jenya705.mcapi.server.module.rest.json;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.github.jenya705.mcapi.server.module.rest.ObjectTunnelFunction;
import lombok.experimental.UtilityClass;

/**
 * @author Jenya705
 */
@UtilityClass
public class JsonUtils {

    public <T, E> JsonDeserializerFunction<E> deserializerTunnel(Class<T> tClass, ObjectTunnelFunction<T, ? extends E> tunnelFunction) {
        return (parser, ctxt) -> tunnelFunction.tunnel(parser.readValueAs(tClass));
    }

    public <T, E> JsonSerializerFunction<T> serializerTunnel(ObjectTunnelFunction<T, E> tunnelFunction) {
        return (value, generator, serializers) -> generator.writeObject(tunnelFunction.tunnel(value));
    }

    public <T, E> JsonDeserializer<E> jacksonDeserializerTunnel(Class<T> tClass, ObjectTunnelFunction<T, ? extends E> tunnelFunction) {
        return new JacksonDeserializer<>(tClass, deserializerTunnel(tClass, tunnelFunction));
    }

    public <T, E> JsonSerializer<T> jacksonSerializerTunnel(Class<T> tClass, ObjectTunnelFunction<T, E> tunnelFunction) {
        return new JacksonSerializer<>(tClass, serializerTunnel(tunnelFunction));
    }
}

package com.github.jenya705.mcapi.server.module.rest.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.github.jenya705.mcapi.server.module.rest.ObjectTunnelFunction;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

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

    public void writeFields(Object obj, JsonGenerator generator) throws IllegalAccessException, IOException {
        generator.writeStartObject();
        Class<?> currentClass = obj.getClass();
        while (currentClass != null) {
            writeFieldsForClass(currentClass, obj, generator);
            currentClass = currentClass.getSuperclass();
        }
        generator.writeEndObject();
    }

    private void writeFieldsForClass(Class<?> clazz, Object obj, JsonGenerator generator) throws IllegalAccessException, IOException {
        for (Field field: clazz.getDeclaredFields()) {
            boolean wasAccessible;
            if (Modifier.isStatic(field.getModifiers())) {
                wasAccessible = field.canAccess(null);
            }
            else {
                wasAccessible = field.canAccess(obj);
            }
            if (!wasAccessible) field.setAccessible(true);
            generator.writeObjectField(field.getName(), field.get(obj));
            if (!wasAccessible) field.setAccessible(false);
        }
    }

}

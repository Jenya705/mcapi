package dev.mcapi.mapper;

@FunctionalInterface
public interface RawDeserializer<T> {

    T deserialize(String raw);

}

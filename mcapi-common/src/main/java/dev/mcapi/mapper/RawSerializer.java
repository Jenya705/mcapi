package dev.mcapi.mapper;

@FunctionalInterface
public interface RawSerializer<T> {

    String serialize(T value);

}

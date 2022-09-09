package dev.mcapi;

public interface Application {

    <T> T getObject(Class<T> clazz);

}

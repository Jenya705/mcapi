package com.github.jenya705.mcapi.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Jenya705
 */
public class CacheClassMap<T> implements Map<Class<?>, T>{

    private final Map<Class<?>, T> map = new HashMap<>();
    private final Map<Class<?>, Class<?>> cache = new HashMap<>();

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return getOrDefault(key, null) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public T get(Object key) {
        return getOrDefault(key, null);
    }

    @Nullable
    @Override
    public T put(Class<?> key, T value) {
        map.put(key, value);
        for (Map.Entry<Class<?>, Class<?>> entry: cache.entrySet()) {
            if (entry.getValue() == key) {
                cache.remove(entry.getKey());
            }
        }
        return value;
    }

    @Override
    public T remove(Object key) {
        for (Map.Entry<Class<?>, Class<?>> entry: cache.entrySet()) {
            if (entry.getValue() == key) {
                cache.remove(entry.getKey());
            }
        }
        return map.remove(key);
    }

    @Override
    public void putAll(@NotNull Map<? extends Class<?>, ? extends T> m) {
        map.putAll(m);
        for (Map.Entry<Class<?>, Class<?>> entry: cache.entrySet()) {
            if (m.containsKey(entry.getValue())) cache.remove(entry.getKey());
        }
    }

    @Override
    public void clear() {
        map.clear();
    }

    @NotNull
    @Override
    public Set<Class<?>> keySet() {
        return map.keySet();
    }

    @NotNull
    @Override
    public Collection<T> values() {
        return map.values();
    }

    @NotNull
    @Override
    public Set<Entry<Class<?>, T>> entrySet() {
        return map.entrySet();
    }

    @Override
    public T getOrDefault(Object key, T defaultValue) {
        Class<?> clazz = (Class<?>) key;
        if (cache.containsKey(clazz)) {
            Class<?> cacheClass = cache.get(clazz);
            if (cacheClass == null) return defaultValue;
            return map.get(cacheClass);
        }
        if (map.containsKey(clazz)) {
            return map.get(clazz);
        }
        for (Map.Entry<Class<?>, T> entry: map.entrySet()) {
            if (entry.getKey().isAssignableFrom(clazz)) {
                cache.put(clazz, entry.getKey());
                return entry.getValue();
            }
        }
        cache.put(clazz, null);
        return defaultValue;
    }
}
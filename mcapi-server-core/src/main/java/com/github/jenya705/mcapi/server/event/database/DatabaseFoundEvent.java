package com.github.jenya705.mcapi.server.event.database;

import com.github.jenya705.mcapi.server.util.MutableSingletonList;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jenya705
 */
@Getter
public class DatabaseFoundEvent<T> {

    private final List<T> entities;

    private final Class<T> originalClass;

    public DatabaseFoundEvent(List<? extends T> entities, Class<T> originalClass) {
        this.entities = new ArrayList<>(entities);
        this.originalClass = originalClass;
    }

    @SuppressWarnings("unchecked")
    public DatabaseFoundEvent(T entity) {
        this.entities = new MutableSingletonList<>(entity);
        this.originalClass = (Class<T>) entity.getClass();
    }

    public boolean isType(Class<?> clazz) {
        return originalClass.isAssignableFrom(clazz);
    }

    /**
     *
     * Returns entity
     *
     * @throws IllegalStateException If there can be more than one entity
     * @return Entity
     */
    public T getSingle() {
        validateSingle();
        return entities.get(0);
    }

    /**
     *
     * Sets entity
     *
     * @throws IllegalStateException If there can be more than one entity
     * @param entity entity to set
     */
    public void setSingle(T entity) {
        validateSingle();
        entities.set(0, entity);
    }

    /**
     *
     * Returns true if it could be found only one entity otherwise false
     *
     * @return true if it could be found single entity otherwise false
     */
    public boolean isSingle() {
        return entities instanceof MutableSingletonList;
    }

    private void validateSingle() {
        if (!isSingle()) {
            throw new IllegalStateException("Entity is not single");
        }
    }

}

package com.github.jenya705.mcapi.server.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MutableValueContainer<T> {

    private T value;

    public ValueContainer<T> immutable() {
        return new ValueContainer<>(value);
    }

}

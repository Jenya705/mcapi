package com.github.jenya705.mcapi.server.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValueContainer<T> {

    private final T value;

}

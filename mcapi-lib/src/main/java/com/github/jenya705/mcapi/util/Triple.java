package com.github.jenya705.mcapi.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Triple<T, V, E> {

    private T first;
    private V second;
    private E third;

}

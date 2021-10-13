package com.github.jenya705.mcapi.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author Jenya705
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pair<T, V> {

    private T left;
    private V right;

}

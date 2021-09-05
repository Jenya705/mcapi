package com.github.jenya705.mcapi.util;

import lombok.experimental.UtilityClass;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Jenya705
 */
@UtilityClass
public class IteratorUtils {

    public <T> Stream<T> stream(Iterator<T> iterator) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                        iterator,
                        Spliterator.ORDERED
                ), false
        );
    }

}

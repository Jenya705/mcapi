package com.github.jenya705.mcapi.ignore;

import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

/**
 * @author Jenya705
 */
@RequiredArgsConstructor
public class SingleIgnoreCriteria implements Predicate<String> {

    private final String className;

    @Override
    public boolean test(String s) {
        return s.equalsIgnoreCase(className);
    }
}

package com.github.jenya705.mcapi.server.ignore;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.function.Predicate;

/**
 * @author Jenya705
 */
@ToString
@RequiredArgsConstructor
public class SubIgnoreCriteria implements Predicate<String> {

    private final String subStart;

    @Override
    public boolean test(String s) {
        return s.startsWith(subStart);
    }
}

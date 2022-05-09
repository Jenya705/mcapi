package com.github.jenya705.mcapi.server.module.options;

import com.github.jenya705.mcapi.server.module.mapper.Mapper;
import com.github.jenya705.mcapi.server.util.MultivaluedMap;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jenya705
 */
@Singleton
public class RawOptionsParserImpl implements RawOptionsParser {

    private static final Pattern parsePattern = Pattern.compile("(\\w*)=([^,]*)\\s*(,|$)");

    private final Mapper mapper;

    @Inject
    public RawOptionsParserImpl(Mapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public RawOptionsMessage parse(String message) {
        return new ContainerRawOptionsMessage(parse(message, mapper));
    }

    public static MultivaluedMap<String, RawOptionsElement> parse(String message, Mapper mapper) {
        Matcher patternMatcher = parsePattern.matcher(message);
        int currentIndex = 0;
        MultivaluedMap<String, RawOptionsElement> result = MultivaluedMap.create();
        while (patternMatcher.find()) {
            if (currentIndex != patternMatcher.start()) {
                throw new IllegalArgumentException(String.format("Failed to parse %s at %d", message, currentIndex));
            }
            currentIndex = patternMatcher.end();
            String name = patternMatcher.group(1);
            String value = patternMatcher.group(2);
            result.add(name, new RawOptionsElementImpl(value, mapper));
        }
        return result;
    }

}

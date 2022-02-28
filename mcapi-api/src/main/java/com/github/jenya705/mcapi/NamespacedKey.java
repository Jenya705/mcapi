package com.github.jenya705.mcapi;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Jenya705
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class NamespacedKey {

    private static final String defaultDomain = "minecraft";

    private final String domain;
    private final String key;

    public static NamespacedKey from(String domain, String key) {
        validateString(domain);
        validateString(key);
        return new NamespacedKey(domain, key);
    }

    public static NamespacedKey minecraft(String key) {
        validateString(key);
        return new NamespacedKey(defaultDomain, key);
    }

    public static NamespacedKey from(String from) {
        int index = from.indexOf(':');
        if (index == -1) {
            return new NamespacedKey(defaultDomain, from);
        }
        if (from.indexOf(':', index+1) != -1) {
            throw new IllegalArgumentException("String contains more than one ':'");
        }
        return new NamespacedKey(
                from.substring(0, index),
                from.substring(index)
        );
    }

    private static void validateString(String str) {
        if (str.contains(":")) {
            throw new IllegalArgumentException("String contains ':'");
        }
    }

    @Override
    public String toString() {
        return domain + ":" + key;
    }
}

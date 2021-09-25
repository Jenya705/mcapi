package com.github.jenya705.mcapi.test;

import com.github.jenya705.mcapi.util.CacheClassMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * @author Jenya705
 */
public class CacheClassMapTest {

    @Test
    public void test() {
        Map<Class<?>, String> cacheMap = new CacheClassMap<>();
        cacheMap.put(CharSequence.class, "String");
        Assertions.assertEquals(cacheMap.get(String.class), "String");
        Assertions.assertEquals(cacheMap.get(CharSequence.class), "String");
    }

}

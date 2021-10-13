package com.github.jenya705.mcapi.test;

import com.github.jenya705.mcapi.HttpMethod;
import com.github.jenya705.mcapi.Route;
import com.github.jenya705.mcapi.module.web.DefaultRoutePredicate;
import com.github.jenya705.mcapi.module.web.RoutePredicate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RequestPredicateTest {

    @Test
    public void firstTest() {
        RoutePredicate predicate = new DefaultRoutePredicate(Route.get("/some/path"));
        Assertions.assertTrue(predicate.apply(HttpMethod.GET, "/some/path"));
        Assertions.assertFalse(predicate.apply(HttpMethod.POST, "/some/path"));
        Assertions.assertFalse(predicate.apply(HttpMethod.GET, "/some/pat"));
        Assertions.assertFalse(predicate.apply(HttpMethod.POST, "/some/pat"));
    }

}

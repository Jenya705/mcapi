package com.github.jenya705.mcapi.test;

import com.github.jenya705.mcapi.module.web.UriParametersParser;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

public class UriParametersParserTest {

    public void firstTest() {
        UriParametersParser parser = new UriParametersParser("/some/{value}/{another}/good");
        Assertions.assertEquals(
                Map.of("value", "hi", "another", "bye"),
                parser.get("/some/hi/bye/good")
        );
    }

}

package com.github.jenya705.mcapi.test;

import com.github.jenya705.mcapi.server.module.mapper.MapperImpl;
import com.github.jenya705.mcapi.server.module.options.RawOptionsMessage;
import com.github.jenya705.mcapi.server.module.options.RawOptionsParser;
import com.github.jenya705.mcapi.server.module.options.RawOptionsParserImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jenya705
 */
public class RawOptionsTest {

    @Test
    public void rawTest() {
        RawOptionsParser parser = new RawOptionsParserImpl(new MapperImpl());
        RawOptionsMessage message = parser.parse("some=some,another=another");
        Assertions.assertEquals("some", message.getOptions("some").get(0).get());
        Assertions.assertEquals("another", message.getOptions("another").get(0).get());
    }

    @Test
    public void quoteTest() {
        RawOptionsParser parser = new RawOptionsParserImpl(new MapperImpl());
        RawOptionsMessage message = parser.parse("some=\"bye hi\",another=another,something=\"hello!! 32\"");
        Assertions.assertEquals("bye hi", message.getOptions("some").get(0).get());
        Assertions.assertEquals("another", message.getOptions("another").get(0).get());
        Assertions.assertEquals("hello!! 32", message.getOptions("something").get(0).get());
    }

}

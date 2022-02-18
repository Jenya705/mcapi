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

}

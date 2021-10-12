package com.github.jenya705.mcapi.test;

import com.github.jenya705.mcapi.stringful.*;
import lombok.Data;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringfulTest {

    @Data
    public static class StringfulData {

        @Index(0)
        private int number;

        @Index(1)
        private String string;

        @Index(2)
        @Argument(required = false)
        private boolean notRequiredBoolean = false;

    }

    @SneakyThrows
    private StringfulParser<StringfulData> parser() {
        return new StringfulParserImpl<>(StringfulData.class);
    }

    private StringfulParseResult<StringfulData> result(StringfulIterator iterator) {
        return parser().create(iterator);
    }

    private StringfulParseResult<StringfulData> result(String str) {
        return parser().create(new ArrayStringfulIterator(str.split(" ")));
    }

    private void errorCauseBad(StringfulParseError error) {
        if (error.causedBy() != null) {
            throw new RuntimeException(error.causedBy());
        }
        throw new RuntimeException(String.format("Bad happened on argument %s", error.onArgument()));
    }

    @Test
    public void goodTest() {
        result("0 string true")
                .ifPresent(data -> {
                    Assertions.assertEquals(0, data.getNumber());
                    Assertions.assertEquals("string", data.getString());
                    Assertions.assertTrue(data.isNotRequiredBoolean());
                })
                .ifFailed(this::errorCauseBad);
    }

    @Test
    public void numberCastException() {
        result("da string true")
                .ifPresent(data -> {
                    throw new RuntimeException("Cast string to integer");
                })
                .ifFailed(error -> {
                    Assertions.assertEquals(NumberFormatException.class, error.causedBy().getClass());
                });
    }

    @Test
    public void goodWithoutNotRequiredTest() {
        result("0 string")
                .ifPresent(data -> {
                    Assertions.assertEquals(0, data.getNumber());
                    Assertions.assertEquals("string", data.getString());
                    Assertions.assertFalse(data.isNotRequiredBoolean());
                })
                .ifFailed(this::errorCauseBad);
    }

}

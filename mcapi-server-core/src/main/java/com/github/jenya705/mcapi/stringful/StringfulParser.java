package com.github.jenya705.mcapi.stringful;

/**
 * @author Jenya705
 */
public interface StringfulParser<T> {

    StringfulParseResult<T> create(StringfulIterator stringfulIterator);
}

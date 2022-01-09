package com.github.jenya705.mcapi.server.stringful;

/**
 * @author Jenya705
 */
public interface StringfulParser<T> {

    StringfulParseResult<T> create(StringfulIterator stringfulIterator);
}

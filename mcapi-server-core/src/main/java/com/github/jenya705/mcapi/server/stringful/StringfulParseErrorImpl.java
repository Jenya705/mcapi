package com.github.jenya705.mcapi.server.stringful;

import lombok.AllArgsConstructor;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class StringfulParseErrorImpl implements StringfulParseError {

    private final Exception cause;
    private final int argumentFailed;

    @Override
    public Exception causedBy() {
        return cause;
    }

    @Override
    public int onArgument() {
        return argumentFailed;
    }

    @Override
    public boolean isParsingFailed() {
        return cause != null;
    }

    @Override
    public boolean isNotEnoughArguments() {
        return !isParsingFailed();
    }
}

package com.github.jenya705.mcapi.server.stringful;

/**
 * @author Jenya705
 */
public interface StringfulParseError {

    Exception causedBy();

    int onArgument();

    boolean isParsingFailed();

    boolean isNotEnoughArguments();
}

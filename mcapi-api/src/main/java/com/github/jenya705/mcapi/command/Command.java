package com.github.jenya705.mcapi.command;

/**
 * @author Jenya705
 */
public interface Command {

    String getName();

    CommandOption[] getOptions();
}

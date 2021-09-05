package com.github.jenya705.mcapi.command;

/**
 * @author Jenya705
 */
public interface ApiCommand {

    String getName();

    ApiCommandOption[] getOptions();
}

package com.github.jenya705.mcapi.command;

/**
 * @author Jenya705
 */
public interface CommandValueOption extends CommandOption {

    boolean isRequired();

    boolean isOnlyFromTab();

    String getTabFunction();

    String[] getSuggestions();
}

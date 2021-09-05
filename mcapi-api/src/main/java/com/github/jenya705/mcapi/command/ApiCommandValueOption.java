package com.github.jenya705.mcapi.command;

/**
 * @author Jenya705
 */
public interface ApiCommandValueOption extends ApiCommandOption {

    boolean isRequired();

    String getTabFunction();

    String[] getSuggestions();
}

package com.github.jenya705.mcapi.event;

/**
 * @author Jenya705
 */
public interface LinkEvent {

    boolean isFailed();

    String[] getDeclinePermissions();

}

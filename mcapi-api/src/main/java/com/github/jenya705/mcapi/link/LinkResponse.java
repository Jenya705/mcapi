package com.github.jenya705.mcapi.link;

/**
 * @author Jenya705
 */
public interface LinkResponse {

    /**
     *
     * If false all others fields not exist
     *
     * @return is linking failed
     */
    boolean isFailed();

    String[] getDeclinePermissions();

}

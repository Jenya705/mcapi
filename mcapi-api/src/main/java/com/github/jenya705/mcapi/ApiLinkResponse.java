package com.github.jenya705.mcapi;

/**
 * @author Jenya705
 */
public interface ApiLinkResponse {

    /**
     * If false all others fields not exist
     *
     * @return is linking failed
     */
    boolean isFailed();

    String[] getDeclinePermissions();
}

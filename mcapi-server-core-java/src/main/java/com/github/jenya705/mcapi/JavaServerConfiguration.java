package com.github.jenya705.mcapi;

/**
 * @since 1.0
 * @author Jenya705
 */
public interface JavaServerConfiguration extends ApiServerConfiguration {

    String getCreateTokenPlayerNameIsNotGivenMessage();

    String getCreateTokenPlayerIsNotExistMessage();

    String getCreateTokenNameIsNotGivenMessage();

    String getCreateTokenSuccess();

}

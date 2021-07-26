package com.github.jenya705.mcapi;

/**
 * @since 1.0
 * @author Jenya705
 */
public interface JavaServerConfiguration extends ApiServerConfiguration {

    String getTokenPlayerNameIsNotGivenMessage();

    String getTokenPlayerIsNotExistMessage();

    String getCreateTokenNameIsNotGivenMessage();

    String getCreateTokenSuccessMessage();

    String getListTokenLayout();

    String getSubContainerCommandNotExistMessage();

    String getSubContainerHelpLayout();

    String getSubContainerHelpDelimiter();

}

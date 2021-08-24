package com.github.jenya705.mcapi;

/**
 * @author Jenya705
 */
public interface ApiError {

    String defaultNamespace = "mcapi";

    int getCode();

    String getNamespace();

    String getReason();

}

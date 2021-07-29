package com.github.jenya705.mcapi;

/**
 * @author Jenya705
 */
public interface ServerModule {

    void load();

    void start();

    void stop();

    ServerApplication getApplication();

}

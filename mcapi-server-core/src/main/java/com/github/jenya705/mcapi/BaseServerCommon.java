package com.github.jenya705.mcapi;

/**
 * @author Jenya705
 */
public abstract class BaseServerCommon {

    protected ServerApplication getApplication() {
        return ServerApplication.getApplication();
    }

    protected ServerApplication app() {
        return getApplication();
    }

    protected ServerCore core() {
        return getApplication().getCore();
    }

}

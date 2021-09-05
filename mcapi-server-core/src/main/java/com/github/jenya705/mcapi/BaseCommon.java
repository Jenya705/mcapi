package com.github.jenya705.mcapi;

/**
 * @author Jenya705
 */
public interface BaseCommon {

    default ServerApplication app() {
        return ServerApplication.getApplication();
    }

    default <T> T bean(Class<? extends T> clazz) {
        return app().getBean(clazz);
    }

    default ServerCore core() {
        return app().getCore();
    }
}

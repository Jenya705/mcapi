package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.module.web.gateway.Gateway;

/**
 * @author Jenya705
 */
public interface BaseCommon {

    ServerApplication app();

    default ServerCore core() {
        return app().getCore();
    }

    default Gateway gateway() {
        return app().getGateway();
    }

    default <T> T bean(Class<? extends T> clazz) {
        return app().getBean(clazz);
    }

    default void autoBeans() {
        app().injectBeansInObject(this);
    }

}

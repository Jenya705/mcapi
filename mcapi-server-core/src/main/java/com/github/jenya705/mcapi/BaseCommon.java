package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.module.web.tunnel.EventTunnel;

/**
 * @author Jenya705
 */
public interface BaseCommon {

    ServerApplication app();

    default ServerCore core() {
        return app().getCore();
    }

    default EventTunnel eventTunnel() {
        return app().getEventTunnel();
    }

    default <T> T bean(Class<? extends T> clazz) {
        return app().getBean(clazz);
    }

    default void autoBeans() {
        app().injectBeansInObject(this);
    }
}

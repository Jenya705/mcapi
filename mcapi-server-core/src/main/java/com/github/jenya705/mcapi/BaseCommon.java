package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.event.EventLoop;
import com.github.jenya705.mcapi.module.web.tunnel.EventTunnel;
import com.github.jenya705.mcapi.worker.Worker;

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

    default Worker worker() {
        return app().getWorker();
    }

    default EventLoop eventLoop() {
        return app().getEventLoop();
    }

    default <T> T bean(Class<? extends T> clazz) {
        return app().getBean(clazz);
    }

    default void autoBeans() {
        app().injectBeansInObject(this);
    }
}

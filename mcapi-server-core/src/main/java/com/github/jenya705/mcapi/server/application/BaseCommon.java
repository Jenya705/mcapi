package com.github.jenya705.mcapi.server.application;

import com.github.jenya705.mcapi.server.ServerCore;
import com.github.jenya705.mcapi.server.event.EventLoop;
import com.github.jenya705.mcapi.server.module.web.tunnel.EventTunnel;
import com.github.jenya705.mcapi.server.worker.Worker;

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

    default boolean debug() {
        return app().isDebug();
    }

    default void debug(Runnable runnable) {
        if (debug()) runnable.run();
    }
}

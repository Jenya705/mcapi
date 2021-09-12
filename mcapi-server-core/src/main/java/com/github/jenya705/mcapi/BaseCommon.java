package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.gateway.GatewayApplication;

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

    default GatewayApplication gateway() {
        return app().getGateway();
    }

}

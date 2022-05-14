package com.github.jenya705.mcapi.server.ss;

import com.github.jenya705.mcapi.server.ss.model.ProxyModel;

/**
 * @author Jenya705
 */
public interface SSConnection {

    void sendModel(ProxyModel model);

    default void sendModel(String type, Object obj) {
        sendModel(new ProxyModel(type, obj));
    }

}

package com.github.jenya705.mcapi.server.ss;

import com.github.jenya705.mcapi.server.module.mapper.Mapper;
import com.github.jenya705.mcapi.server.ss.model.ProxyModel;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;

/**
 * @author Jenya705
 */
@RequiredArgsConstructor
public class ReactorSSConnection implements SSConnection {

    private final Connection connection;
    private final Mapper mapper;

    @Override
    public void sendModel(ProxyModel model) {
        connection.outbound().sendString(Mono.just(mapper.asJson(model)));
    }
}

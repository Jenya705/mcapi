package com.github.jenya705.mcapi.server.event.application;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@RequiredArgsConstructor
public class ApplicationClassActionEvent {

    public enum Action {
        INIT,
        START,
        STOP
    }

    private final Object bean;
    private final Action action;

    private boolean cancelled = false;

}

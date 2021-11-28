package com.github.jenya705.mcapi;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jenya705
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ApplicationClassActionEvent {

    enum Action {
        INIT,
        START,
        STOP
    }

    private final Class<?> beanClass;
    private final Action action;

    private boolean cancelled = false;

}

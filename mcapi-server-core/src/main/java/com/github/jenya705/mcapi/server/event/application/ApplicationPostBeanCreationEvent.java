package com.github.jenya705.mcapi.server.event.application;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicationPostBeanCreationEvent {

    private Object beanObject;

    public void cancel() {
        beanObject = null;
    }

}

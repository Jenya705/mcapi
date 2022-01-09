package com.github.jenya705.mcapi.server.event.application;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicationPreBeanCreationEvent {

    private Class<?> bean;

    public void cancel() {
        bean = null;
    }

}

package com.github.jenya705.mcapi.server.event.database;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@RequiredArgsConstructor
public class DatabaseUpdateDoneEvent {

    private final Object entity;

    private final DatabaseUpdateEvent.Result result;

}

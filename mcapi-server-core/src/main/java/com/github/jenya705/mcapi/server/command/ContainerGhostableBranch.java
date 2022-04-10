package com.github.jenya705.mcapi.server.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Delegate;

import java.util.Map;

/**
 * @author Jenya705
 */
@Getter
@AllArgsConstructor
public class ContainerGhostableBranch implements GhostableBranch, Map<String, Object> {

    private final boolean ghost;
    @Delegate
    private final Map<String, Object> node;
}

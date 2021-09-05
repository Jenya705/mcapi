package com.github.jenya705.mcapi.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Delegate;

import java.util.Map;

/**
 * @author Jenya705
 */
@Getter
@AllArgsConstructor
public class ContainerGhostBranch implements GhostBranch, Map<String, Object> {

    private final boolean ghost;
    @Delegate
    private final Map<String, Object> node;
}

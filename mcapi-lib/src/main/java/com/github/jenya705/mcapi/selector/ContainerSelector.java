package com.github.jenya705.mcapi.selector;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

/**
 * @author Jenya705
 */
@Getter
@AllArgsConstructor
public class ContainerSelector implements Selector {

    private final String stringValue;
    private final String permission;
    private final UUID target;

    @Override
    public String asString() {
        return stringValue;
    }
}

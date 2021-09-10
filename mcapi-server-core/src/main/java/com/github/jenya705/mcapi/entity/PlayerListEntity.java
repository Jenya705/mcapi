package com.github.jenya705.mcapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;
import java.util.UUID;

/**
 * @author Jenya705
 */
@Getter
@AllArgsConstructor(staticName = "of")
public class PlayerListEntity {
    private final Collection<UUID> uuids;
}

package com.github.jenya705.mcapi;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

/**
 * @author Jenya705
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerID {

    private final String id;

    public static PlayerID of(String name) {
        return new PlayerID(name);
    }

    public static PlayerID of(UUID uuid) {
        return new PlayerID(uuid.toString());
    }

}

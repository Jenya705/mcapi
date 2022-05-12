package com.github.jenya705.mcapi.player;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

/**
 * @author Jenya705
 */
@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerID {

    public static PlayerID empty() {
        return new PlayerID(null, null);
    }

    public static PlayerID uuid(UUID uuid) {
        return new PlayerID(uuid, null);
    }

    public static PlayerID nickname(String nickname) {
        return new PlayerID(null, nickname);
    }

    private final UUID uuid;
    private final String nickname;

    public boolean isEmpty() {
        return uuid == null && nickname == null;
    }

    public boolean isUUID() {
        return uuid != null;
    }

    public boolean isNickname() {
        return nickname != null;
    }

    @Override
    public String toString() {
        return isUUID() ? uuid.toString() : nickname;
    }

}

package com.github.jenya705.mcapi.util;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.ServerCore;
import com.github.jenya705.mcapi.error.PlayerIdFormatException;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@UtilityClass
public class PlayerUtils {

    public Optional<ApiPlayer> getPlayer(String name, ServerCore core) {
        Pair<Optional<ApiPlayer>, Boolean> information = getPlayerWithFullInformation(name, core);
        if (information.getRight()) {
            throw new PlayerIdFormatException(name);
        }
        return information.getLeft();
    }

    public Optional<ApiPlayer> getPlayerWithoutException(String name, ServerCore core) {
        return getPlayerWithFullInformation(name, core).getLeft();
    }

    private Pair<Optional<ApiPlayer>, Boolean> getPlayerWithFullInformation(String name, ServerCore core) {
        if (name.length() < 17) {
            if (name.length() < 3) {
                return new Pair<>(Optional.empty(), true);
            }
            return new Pair<>(core.getOptionalPlayer(name), false);
        }
        else if (name.length() == 32) {
            return new Pair<>(core.getOptionalPlayer(
                    UUID.fromString(
                            name.replaceFirst(
                                    "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"
                            )
                    )
            ), false);
        }
        else if (name.length() == 36) {
            return new Pair<>(core.getOptionalPlayer(UUID.fromString(name)), false);
        }
        else {
            return new Pair<>(Optional.empty(), true);
        }
    }

    public List<String> playerTabs(ServerCore core) {
        return core
                .getPlayers()
                .stream()
                .map(ApiPlayer::getName)
                .collect(Collectors.toList());
    }

    public UUID parseUuid(String uuid) {
        if (uuid.length() == 32) {
            return UUID.fromString(
                    uuid.replaceFirst(
                            "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"
                    )
            );
        }
        else if (uuid.length() == 36) {
            return UUID.fromString(uuid);
        }
        return null;
    }

    public Optional<UUID> optionalUuid(String uuid) {
        return Optional.ofNullable(parseUuid(uuid));
    }

}

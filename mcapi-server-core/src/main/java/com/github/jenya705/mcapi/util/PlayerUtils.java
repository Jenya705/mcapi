package com.github.jenya705.mcapi.util;

import com.github.jenya705.mcapi.ApiOfflinePlayer;
import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.ServerCore;
import com.github.jenya705.mcapi.error.PlayerIdFormatException;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@UtilityClass
public class PlayerUtils {

    private final Random random = new Random();

    public Optional<? extends ApiPlayer> getPlayer(String name, ServerCore core) {
        Pair<Optional<? extends ApiPlayer>, Boolean> information = getPlayerWithFullInformation(name, core);
        if (!information.getRight()) {
            throw new PlayerIdFormatException(name);
        }
        return information.getLeft();
    }

    public Optional<? extends ApiPlayer> getPlayerWithoutException(String name, ServerCore core) {
        return getPlayerWithFullInformation(name, core).getLeft();
    }

    public Optional<? extends ApiOfflinePlayer> getOfflinePlayer(String name, ServerCore core) {
        Pair<Optional<? extends ApiOfflinePlayer>, Boolean> information = getOfflinePlayerWithFullInformation(name, core);
        if (!information.getRight()) {
            throw new PlayerIdFormatException(name);
        }
        return information.getLeft();
    }

    public Optional<? extends ApiOfflinePlayer> getOfflinePlayerWithoutException(String name, ServerCore core) {
        return getOfflinePlayerWithFullInformation(name, core).getLeft();
    }

    private Pair<Optional<? extends ApiPlayer>, Boolean> getPlayerWithFullInformation(String name, ServerCore core) {
        Object id = parsePlayerId(name);
        if (id == null) return new Pair<>(Optional.empty(), false);
        if (id instanceof UUID) {
            return new Pair<>(core.getOptionalPlayer((UUID) id), true);
        }
        else {
            return new Pair<>(core.getOptionalPlayer(id.toString()), true);
        }
    }

    private Pair<Optional<? extends ApiOfflinePlayer>, Boolean> getOfflinePlayerWithFullInformation(String name, ServerCore core) {
        Object id = parsePlayerId(name);
        if (id == null) return new Pair<>(Optional.empty(), false);
        if (id instanceof UUID) {
            return new Pair<>(core.getOptionalOfflinePlayer((UUID) id), true);
        }
        else {
            return new Pair<>(core.getOptionalOfflinePlayer(id.toString()), true);
        }
    }

    public Object parsePlayerId(String name) {
        if (name == null) return null;
        if (name.length() < 17) {
            if (name.length() < 3) {
                return null;
            }
            return name;
        }
        else if (name.length() == 32) {
            return UUID.fromString(
                    name.replaceFirst(
                            "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"
                    )
            );
        }
        else if (name.length() == 36) {
            return UUID.fromString(name);
        }
        else {
            return null;
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

package com.github.jenya705.mcapi.server.util;

import com.github.jenya705.mcapi.player.OfflinePlayer;
import com.github.jenya705.mcapi.error.PlayerIdFormatException;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.ServerCore;
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

    public Optional<? extends Player> getPlayer(String name, ServerCore core) {
        Pair<Player, Boolean> information = getPlayerWithFullInformation(name, core);
        if (!information.getRight()) {
            throw PlayerIdFormatException.create(name);
        }
        return Optional.ofNullable(information.getLeft());
    }

    public Optional<? extends Player> getPlayerWithoutException(String name, ServerCore core) {
        return Optional.ofNullable(getPlayerWithFullInformation(name, core).getLeft());
    }

    public Optional<? extends OfflinePlayer> getOfflinePlayer(String name, ServerCore core) {
        Pair<OfflinePlayer, Boolean> information = getOfflinePlayerWithFullInformation(name, core);
        if (!information.getRight()) {
            throw PlayerIdFormatException.create(name);
        }
        return Optional.ofNullable(information.getLeft());
    }

    public Optional<? extends OfflinePlayer> getOfflinePlayerWithoutException(String name, ServerCore core) {
        return Optional.ofNullable(getOfflinePlayerWithFullInformation(name, core).getLeft());
    }

    private Pair<Player, Boolean> getPlayerWithFullInformation(String name, ServerCore core) {
        Object id = parsePlayerId(name);
        if (id == null) return new Pair<>(null, false);
        if (id instanceof UUID) {
            return new Pair<>(core.getPlayer((UUID) id), true);
        }
        else {
            return new Pair<>(core.getPlayer(id.toString()), true);
        }
    }

    private Pair<OfflinePlayer, Boolean> getOfflinePlayerWithFullInformation(String name, ServerCore core) {
        Object id = parsePlayerId(name);
        if (id == null) return new Pair<>(null, false);
        if (id instanceof UUID) {
            return new Pair<>(core.getOfflinePlayer((UUID) id), true);
        }
        else {
            return new Pair<>(core.getOfflinePlayer(id.toString()), true);
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
        return parseUuid(name);
    }

    public List<String> playerTabs(ServerCore core) {
        return core
                .getPlayers()
                .stream()
                .map(Player::getName)
                .collect(Collectors.toList());
    }

    public UUID parseUuid(String uuid) {
        if (uuid.length() == 32) {
            return parseUUIDWithoutDashes(uuid);
        }
        else if (uuid.length() == 36) {
            return UUID.fromString(uuid);
        }
        return null;
    }

    public Optional<UUID> optionalUuid(String uuid) {
        return Optional.ofNullable(parseUuid(uuid));
    }

    private UUID parseUUIDWithoutDashes(String uuid) {
        long most = Long.parseLong(uuid, 0, 8, 16) << 32 |
                Long.parseLong(uuid, 8, 16, 16);
        long least = Long.parseLong(uuid, 16, 24, 16) << 32 |
                Long.parseLong(uuid, 24, 32, 16);
        return new UUID(most, least);
    }

}

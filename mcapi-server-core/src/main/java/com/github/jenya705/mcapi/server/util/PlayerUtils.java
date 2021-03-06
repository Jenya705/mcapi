package com.github.jenya705.mcapi.server.util;

import com.github.jenya705.mcapi.error.PlayerIdFormatException;
import com.github.jenya705.mcapi.player.OfflinePlayer;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.player.PlayerID;
import com.github.jenya705.mcapi.server.ServerCore;
import lombok.experimental.UtilityClass;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Jenya705
 */
@UtilityClass
public class PlayerUtils {

    public Mono<Player> getPlayer(String name, ServerCore core) {
        Pair<Mono<Player>, Boolean> information = getPlayerWithFullInformation(name, core);
        if (!information.getRight()) {
            throw PlayerIdFormatException.create(name);
        }
        return information.getLeft();
    }

    public Mono<Player> getPlayerWithoutException(String name, ServerCore core) {
        return getPlayerWithFullInformation(name, core).getLeft();
    }

    public Mono<OfflinePlayer> getOfflinePlayer(String name, ServerCore core) {
        Pair<Mono<OfflinePlayer>, Boolean> information = getOfflinePlayerWithFullInformation(name, core);
        if (!information.getRight()) {
            throw PlayerIdFormatException.create(name);
        }
        return information.getLeft();
    }

    public Mono<OfflinePlayer> getOfflinePlayerWithoutException(String name, ServerCore core) {
        return getOfflinePlayerWithFullInformation(name, core).getLeft();
    }

    private Pair<Mono<Player>, Boolean> getPlayerWithFullInformation(String name, ServerCore core) {
        PlayerID id = parsePlayerId(name);
        if (id.isUUID()) {
            return new Pair<>(core.getPlayer(id.getUuid()), true);
        }
        else if (id.isNickname()) {
            return new Pair<>(core.getPlayer(id.getNickname()), true);
        }
        return new Pair<>(null, false);
    }

    private Pair<Mono<OfflinePlayer>, Boolean> getOfflinePlayerWithFullInformation(String name, ServerCore core) {
        PlayerID id = parsePlayerId(name);
        if (id.isUUID()) {
            return new Pair<>(core.getOfflinePlayer(id.getUuid()), true);
        }
        else if (id.isNickname()) {
            return new Pair<>(core.getOfflinePlayer(id.getNickname()), true);
        }
        return new Pair<>(null, null);
    }

    public PlayerID parsePlayerId(String name) {
        if (name == null) return PlayerID.empty();
        if (name.length() < 17) {
            if (name.length() < 3) {
                return PlayerID.empty();
            }
            return PlayerID.nickname(name);
        }
        return PlayerID.uuid(parseUuid(name));
    }

    public List<String> playerTabs(ServerCore core) {
        return core
                .getPlayers()
                .map(Player::getName)
                .collectList()
                .block();
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

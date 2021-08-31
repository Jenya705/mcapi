package com.github.jenya705.mcapi.util;

import com.github.jenya705.mcapi.ApiOfflinePlayer;
import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.ServerCore;
import com.github.jenya705.mcapi.error.PlayerIdFormatException;
import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@UtilityClass
public class PlayerUtils {

    private final Random random = new Random();

    public Optional<ApiPlayer> getPlayer(String name, ServerCore core) {
        Pair<Optional<ApiPlayer>, Boolean> information = getPlayerWithFullInformation(name, core);
        if (!information.getRight()) {
            throw new PlayerIdFormatException(name);
        }
        return information.getLeft();
    }

    public Optional<ApiPlayer> getPlayerWithoutException(String name, ServerCore core) {
        return getPlayerWithFullInformation(name, core).getLeft();
    }

    public Optional<ApiOfflinePlayer> getOfflinePlayer(String name, ServerCore core) {
        Pair<Optional<ApiOfflinePlayer>, Boolean> information = getOfflinePlayerWithFullInformation(name, core);
        if (!information.getRight()) {
            throw new PlayerIdFormatException(name);
        }
        return information.getLeft();
    }

    public Optional<ApiOfflinePlayer> getOfflinePlayerWithoutException(String name, ServerCore core) {
        return getOfflinePlayerWithFullInformation(name, core).getLeft();
    }

    private Pair<Optional<ApiPlayer>, Boolean> getPlayerWithFullInformation(String name, ServerCore core) {
        Object id = parsePlayerId(name);
        if (id == null) return new Pair<>(Optional.empty(), false);
        if (id instanceof UUID) {
            return new Pair<>(core.getOptionalPlayer((UUID) id), true);
        }
        else {
            return new Pair<>(core.getOptionalPlayer(id.toString()), true);
        }
    }

    private Pair<Optional<ApiOfflinePlayer>, Boolean> getOfflinePlayerWithFullInformation(String name, ServerCore core) {
        Object id = parsePlayerId(name);
        if (id == null) return new Pair<>(Optional.empty(), false);
        if (id instanceof UUID) {
            return new Pair<>(core.getOptionalOfflinePlayer((UUID) id), true);
        }
        else {
            return new Pair<>(core.getOptionalOfflinePlayer(id.toString()), true);
        }
    }

    private Object parsePlayerId(String name) {
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

    public Selector<ApiPlayer> parseSelector(String selector, ServerCore core) {
        if (core.getPlayers().isEmpty()) return SelectorImpl.empty();
        Object id = parsePlayerId(selector);
        if (id == null) {
            if (selector.equals("@a")) {
                return new SelectorImpl<>(
                        core.getPlayers(),
                        ".@a",
                        null
                );
            }
            if (selector.equals("@r")) {
                List<ApiPlayer> apiPlayers = new ArrayList<>(core.getPlayers());
                return new SelectorImpl<>(
                        Collections.singletonList(
                                apiPlayers.get(
                                        random.nextInt(apiPlayers.size())
                                )
                        ),
                        ".@r",
                        null
                );
            }
            return SelectorImpl.empty();
        }
        if (id instanceof UUID) {
            ApiPlayer player = core.getPlayer((UUID) id);
            if (player == null) return SelectorImpl.empty();
            return new SelectorImpl<>(Collections.singletonList(player), "", player.getUuid());
        }
        ApiPlayer player = core.getPlayer(id.toString());
        if (player == null) return SelectorImpl.empty();
        return new SelectorImpl<>(Collections.singletonList(player), "", player.getUuid());
    }

    public Optional<UUID> optionalUuid(String uuid) {
        return Optional.ofNullable(parseUuid(uuid));
    }
}

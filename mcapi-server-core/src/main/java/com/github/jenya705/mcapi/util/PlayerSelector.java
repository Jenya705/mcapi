package com.github.jenya705.mcapi.util;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.ServerCore;
import com.github.jenya705.mcapi.entity.AbstractBot;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jenya705
 */
@UtilityClass
public class PlayerSelector {

    private static final Random random = new Random();

    private static final Map<String, BiFunction<ServerCore, AbstractBot, Selector<ApiPlayer>>> selectors = Map.of(
            "@a", (core, bot) -> new SelectorContainer<>(
                    core.getPlayers(),
                    ".@a",
                    null
            ),
            "@r", (core, bot) -> {
                List<ApiPlayer> players = new ArrayList<>(core.getPlayers());
                if (players.isEmpty()) return SelectorContainer.empty();
                return new SelectorContainer<>(
                        players.get(random.nextInt(players.size())),
                        ".@r",
                        null
                );
            },
            "@l", (core, bot) -> new SelectorContainer<>(
                    bot
                            .getLinks()
                            .stream()
                            .map(it -> core.getPlayer(it.getTarget()))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toSet()),
                    ".@l",
                    null
            )
    );

    public static Selector<ApiPlayer> of(String selectorString, ServerCore core, @Nullable AbstractBot bot) {
        Object playerId = PlayerUtils.parsePlayerId(selectorString);
        if (playerId != null) {
            ApiPlayer player = null;
            if (playerId instanceof UUID) player = core.getPlayer((UUID) playerId);
            if (playerId instanceof String) player = core.getPlayer((String) playerId);
            if (player == null) return SelectorContainer.empty();
            return new SelectorContainer<>(
                    player, "", player.getUuid()
            );
        }
        BiFunction<ServerCore, AbstractBot, Selector<ApiPlayer>> function
                = selectors.getOrDefault(selectorString, null);
        if (function == null) return SelectorContainer.empty();
        return function.apply(core, bot);
    }
}

package com.github.jenya705.mcapi.module.selector;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.entity.BotLinkEntity;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
public class PlayerSelectorCreator extends MapSelectorCreator<ApiPlayer, PlayerSelectorCreator.Data> implements BaseCommon {

    private final Random random = new Random();

    @lombok.Data
    @AllArgsConstructor
    public static class Data {
        private final AbstractBot bot;
    }

    private PlayerSelectorCreator() {
        this
                .uuidDirect((data, id) ->
                        core()
                                .getOptionalPlayerId(id)
                                .orElseThrow(() -> new PlayerNotFoundException(id))
                )
                .defaultSelector("a", data ->
                        core().getPlayers()
                )
                .defaultSelector("r", data -> {
                    List<ApiPlayer> playerList = new ArrayList<>(core().getPlayers());
                    return Collections.singletonList(
                            playerList.get(
                                    random.nextInt(playerList.size())
                            )
                    );
                })
                .defaultSelector("l", data ->
                        Optional
                                .ofNullable(data.getBot())
                                .map(AbstractBot::getLinks)
                                .map(links -> links
                                        .stream()
                                        .map(BotLinkEntity::getTarget)
                                        .map(target -> core().getPlayer(target))
                                        .filter(Objects::nonNull)
                                        .collect(Collectors.toList())
                                )
                                .orElseGet(Collections::emptyList)
                );
    }

    public static final PlayerSelectorCreator singleton = new PlayerSelectorCreator();

    public static PlayerSelectorCreator get() {
        return singleton;
    }

}

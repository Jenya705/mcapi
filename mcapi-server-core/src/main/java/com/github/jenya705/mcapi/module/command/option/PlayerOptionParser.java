package com.github.jenya705.mcapi.module.command.option;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.jenya705.mcapi.ApiOfflinePlayer;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.command.ApiCommandValueOption;
import com.github.jenya705.mcapi.command.types.PlayerOption;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import com.github.jenya705.mcapi.util.PlayerUtils;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
public class PlayerOptionParser extends AbstractCommandValueOptionParser implements BaseCommon {

    public PlayerOptionParser() {
        this
                .tab(tabDefault, (option, bot) -> {
                    PlayerOption realOption = (PlayerOption) option;
                    if (realOption.isOnlyLinked()) {
                        return bot
                                .getLinks()
                                .stream()
                                .map(it -> core().getPlayer(it.getTarget()))
                                .filter(Objects::nonNull)
                                .map(ApiOfflinePlayer::getName)
                                .collect(Collectors.toList());
                    }
                    return PlayerUtils.playerTabs(core());
                });
    }

    @Override
    public ApiCommandValueOption deserialize(JsonNode node) {
        return new PlayerOption(
                node.get("name").asText(),
                defaultNode(node.get("required"), false, JsonNode::asBoolean),
                getTabs(node),
                defaultNode(node.get("onlyFromTab"), false, JsonNode::asBoolean),
                defaultNode(node.get("onlyLinked"), false, JsonNode::asBoolean)
        );
    }

    @Override
    public Object serialize(ApiCommandValueOption option, AbstractBot owner, String value) {
        return core()
                .getOptionalPlayerId(value)
                .orElseThrow(() -> new PlayerNotFoundException(value))
                .getUuid();
    }
}
package com.github.jenya705.mcapi.server.module.command.option.value;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.jenya705.mcapi.OfflinePlayer;
import com.github.jenya705.mcapi.command.CommandValueOption;
import com.github.jenya705.mcapi.command.types.PlayerOption;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import com.github.jenya705.mcapi.server.application.BaseCommon;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.util.PlayerUtils;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
public class PlayerOptionParser extends AbstractCommandValueOptionParser implements BaseCommon {

    private final ServerApplication application;

    @Override
    public ServerApplication app() {
        return application;
    }

    public PlayerOptionParser(ServerApplication application) {
        this.application = application;
        this
                .tab(tabDefault, (option, bot) -> {
                    PlayerOption realOption = (PlayerOption) option;
                    if (realOption.isOnlyLinked()) {
                        return bot
                                .getLinks()
                                .stream()
                                .map(it -> core().getPlayer(it.getTarget()))
                                .filter(Objects::nonNull)
                                .map(OfflinePlayer::getName)
                                .collect(Collectors.toList());
                    }
                    return PlayerUtils.playerTabs(core());
                });
    }

    @Override
    public CommandValueOption valueDeserialize(JsonNode node) {
        return new PlayerOption(
                node.get("name").asText(),
                defaultNode(node.get("required"), false, JsonNode::asBoolean),
                getTabs(node.get("tab")),
                defaultNode(node.get("onlyFromTab"), false, JsonNode::asBoolean),
                defaultNode(node.get("onlyLinked"), false, JsonNode::asBoolean)
        );
    }

    @Override
    public Object serialize(CommandValueOption option, AbstractBot owner, String value) {
        return core()
                .getOptionalPlayerId(value)
                .orElseThrow(() -> PlayerNotFoundException.create(value))
                .getUuid();
    }
}

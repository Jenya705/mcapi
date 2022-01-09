package com.github.jenya705.mcapi.server.module.command.option.value;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.jenya705.mcapi.command.CommandValueOption;
import com.github.jenya705.mcapi.command.types.IntegerOption;
import com.github.jenya705.mcapi.server.entity.AbstractBot;

/**
 * @author Jenya705
 */
public class IntegerOptionParser extends AbstractCommandValueOptionParser {

    @Override
    public CommandValueOption valueDeserialize(JsonNode node) {
        return new IntegerOption(
                node.get("name").asText(),
                defaultNode(node.get("required"), false, JsonNode::asBoolean),
                getTabs(node),
                defaultNode(node.get("onlyFromTab"), false, JsonNode::asBoolean),
                defaultNode(node.get("max"), Integer.MAX_VALUE, JsonNode::asInt),
                defaultNode(node.get("min"), Integer.MIN_VALUE, JsonNode::asInt)
        );
    }

    @Override
    public Object serialize(CommandValueOption option, AbstractBot owner, String value) {
        IntegerOption realOption = (IntegerOption) option;
        int realValue = Integer.parseInt(value);
        if (realOption.getMax() <= realValue || realOption.getMin() >= realValue) {
            throw new IllegalArgumentException(String.format(
                    "Integer must be in range from %s to %s", realOption.getMin(), realOption.getMax()
            ));
        }
        return realValue;
    }
}

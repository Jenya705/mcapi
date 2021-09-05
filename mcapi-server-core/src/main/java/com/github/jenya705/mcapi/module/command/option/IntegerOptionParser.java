package com.github.jenya705.mcapi.module.command.option;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.jenya705.mcapi.command.ApiCommandValueOption;
import com.github.jenya705.mcapi.command.types.IntegerOption;
import com.github.jenya705.mcapi.entity.AbstractBot;

/**
 * @author Jenya705
 */
public class IntegerOptionParser extends AbstractCommandValueOptionParser {

    @Override
    public ApiCommandValueOption deserialize(JsonNode node) {
        return new IntegerOption(
                node.get("name").asText(),
                defaultNode(node.get("required"), false, JsonNode::asBoolean),
                getTabs(node),
                defaultNode(node.get("onlyFromTab"), false, JsonNode::asBoolean),
                node.get("max").asInt(Integer.MAX_VALUE),
                node.get("min").asInt(Integer.MIN_VALUE)
        );
    }

    @Override
    public Object serialize(ApiCommandValueOption option, AbstractBot owner, String value) {
        IntegerOption realOption = (IntegerOption) option;
        int realValue = Integer.parseInt(value);
        if (realOption.getMax() > realValue || realOption.getMin() < realValue) {
            throw new IllegalArgumentException(String.format(
                    "Integer must be in range from %s to %s", realOption.getMin(), realOption.getMax()
            ));
        }
        return realValue;
    }
}

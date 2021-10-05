package com.github.jenya705.mcapi.module.command.option.value;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.jenya705.mcapi.command.ApiCommandOption;
import com.github.jenya705.mcapi.command.ApiCommandValueOption;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.module.command.CommandOptionParser;
import com.github.jenya705.mcapi.util.IteratorUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Jenya705
 */
public abstract class AbstractCommandValueOptionParser implements CommandOptionParser {

    public static final String tabDefault = "__default__";

    private static final String[] empty = new String[0];

    @FunctionalInterface
    interface DefaultDeserializeConstructor {
        ApiCommandValueOption get(String name, boolean required, Object tab, boolean onlyFromTab);
    }

    private final Map<String, BiFunction<ApiCommandValueOption, AbstractBot, List<String>>> tabFunctions = new HashMap<>();

    protected Object getTabs(JsonNode tabsNode) {
        if (tabsNode == null) {
            if (tabFunctions.containsKey(tabDefault)) return tabDefault;
            return empty;
        }
        if (tabsNode.isTextual()) {
            String function = tabsNode.asText();
            if (!tabFunctions.containsKey(function)) {
                throw new IllegalArgumentException(String.format("Not exist tab function %s", function));
            }
            return function;
        }
        return IteratorUtils
                .stream(tabsNode.elements())
                .map(JsonNode::asText)
                .toArray(String[]::new);
    }

    protected <T> T defaultNode(JsonNode node, T defaultValue, Function<JsonNode, T> function) {
        return node == null ? defaultValue : function.apply(node);
    }

    @Override
    public final ApiCommandOption deserialize(JsonNode node) {
        return valueDeserialize(node);
    }

    @Override
    public final Object serialize(ApiCommandOption option, AbstractBot owner, String value) {
        return serialize((ApiCommandValueOption) option, owner, value);
    }

    public abstract ApiCommandValueOption valueDeserialize(JsonNode node);

    public abstract Object serialize(ApiCommandValueOption option, AbstractBot owner, String value);

    protected ApiCommandValueOption defaultDeserialize(DefaultDeserializeConstructor constructor, JsonNode node) {
        return constructor.get(
                node.get("name").asText(),
                defaultNode(node.get("required"), false, JsonNode::asBoolean),
                getTabs(node.get("tabs")),
                defaultNode(node.get("onlyFromTab"), false, JsonNode::asBoolean)
        );
    }

    @Override
    public List<String> tabs(ApiCommandOption option, AbstractBot owner) {
        ApiCommandValueOption realOption = (ApiCommandValueOption) option;
        return realOption.getSuggestions() == null ?
                tabFunctions.get(realOption.getTabFunction()).apply(realOption, owner) :
                Arrays.asList(realOption.getSuggestions());
    }

    public AbstractCommandValueOptionParser tab(String name, BiFunction<ApiCommandValueOption, AbstractBot, List<String>> function) {
        tabFunctions.put(name, function);
        return this;
    }
}

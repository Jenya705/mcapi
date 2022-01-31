package com.github.jenya705.mcapi.server.module.command.option.value;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.jenya705.mcapi.command.CommandOption;
import com.github.jenya705.mcapi.command.CommandValueOption;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.module.command.CommandOptionParser;
import com.github.jenya705.mcapi.server.module.rest.json.JsonUtils;
import com.github.jenya705.mcapi.server.util.IteratorUtils;
import lombok.SneakyThrows;

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
        CommandValueOption get(String name, boolean required, Object tab, boolean onlyFromTab);
    }

    private final Map<String, BiFunction<CommandValueOption, AbstractBot, List<String>>> tabFunctions = new HashMap<>();

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
    public final CommandOption deserialize(JsonNode node) {
        return valueDeserialize(node);
    }

    @Override
    public final Object serialize(CommandOption option, AbstractBot owner, String value) {
        return serialize((CommandValueOption) option, owner, value);
    }

    public abstract CommandValueOption valueDeserialize(JsonNode node);

    public abstract Object serialize(CommandValueOption option, AbstractBot owner, String value);

    protected CommandValueOption defaultDeserialize(DefaultDeserializeConstructor constructor, JsonNode node) {
        return constructor.get(
                node.get("name").asText(),
                defaultNode(node.get("required"), false, JsonNode::asBoolean),
                getTabs(node.get("tab")),
                defaultNode(node.get("onlyFromTab"), false, JsonNode::asBoolean)
        );
    }

    @Override
    public List<String> tabs(CommandOption option, AbstractBot owner) {
        CommandValueOption realOption = (CommandValueOption) option;
        return realOption.getSuggestions() == null ?
                tabFunctions.get(realOption.getTabFunction()).apply(realOption, owner) :
                Arrays.asList(realOption.getSuggestions());
    }

    @Override
    @SneakyThrows
    public void write(CommandOption option, JsonGenerator generator) {
        JsonUtils.writeFields(option, generator);
    }

    public AbstractCommandValueOptionParser tab(String name, BiFunction<CommandValueOption, AbstractBot, List<String>> function) {
        tabFunctions.put(name, function);
        return this;
    }
}

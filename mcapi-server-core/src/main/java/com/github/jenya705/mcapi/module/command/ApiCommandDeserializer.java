package com.github.jenya705.mcapi.module.command;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.jenya705.mcapi.command.ApiCommand;
import com.github.jenya705.mcapi.command.ApiCommandExecutableOption;
import com.github.jenya705.mcapi.command.ApiCommandOption;
import com.github.jenya705.mcapi.command.ApiCommandValueOption;
import com.github.jenya705.mcapi.command.entity.ApiCommandEntity;
import com.github.jenya705.mcapi.command.types.*;
import com.github.jenya705.mcapi.util.IteratorUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntFunction;

/**
 * @author Jenya705
 */
public class ApiCommandDeserializer extends StdDeserializer<ApiCommand> {

    private static final Map<String, Function<JsonNode, ApiCommandOption>> optionDeserializers = new HashMap<>();

    private static Object getTabs(JsonNode tabsNode) {
        if (tabsNode == null) return null;
        if (tabsNode.isTextual()) {
            return tabsNode.asText();
        }
        return IteratorUtils
                .stream(tabsNode.elements())
                .map(JsonNode::asText)
                .toArray(String[]::new);
    }

    @SuppressWarnings("unchecked")
    private static <T extends ApiCommandOption> T[] getSubOptions(JsonNode node, IntFunction<T[]> arrayConstructor) {
        return IteratorUtils.stream(
                node
                        .get("options")
                        .elements()
        )
                .map(it -> (T) optionDeserializers.get(it.get("type").asText()).apply(it))
                .toArray(arrayConstructor);
    }

    static {
        optionDeserializers.put(StringOption.type, node -> new StringOption(
                node.get("name").asText(),
                node.get("required").asBoolean(),
                getTabs(node.get("tabs"))
        ));
        optionDeserializers.put(IntegerOption.type, node -> new IntegerOption(
                node.get("name").asText(),
                node.get("required").asBoolean(),
                getTabs(node.get("tabs")),
                node.get("max").asInt(Integer.MAX_VALUE),
                node.get("min").asInt(Integer.MIN_VALUE)
        ));
        optionDeserializers.put(BooleanOption.type, node -> new BooleanOption(
                node.get("name").asText(),
                node.get("required").asBoolean(),
                getTabs(node.get("tabs"))
        ));
        optionDeserializers.put(SubCommandOption.type, node -> new SubCommandOption(
                node.get("name").asText(),
                getSubOptions(node, ApiCommandValueOption[]::new)
        ));
        optionDeserializers.put(SubCommandGroupOption.type, node -> new SubCommandGroupOption(
                node.get("name").asText(),
                getSubOptions(node, ApiCommandExecutableOption[]::new)
        ));
    }

    public ApiCommandDeserializer() {
        this(null);
    }

    public ApiCommandDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ApiCommand deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        return new ApiCommandEntity(
                node.get("name").asText(),
                getSubOptions(node, ApiCommandOption[]::new)
        );
    }
}

package com.github.jenya705.mcapi.server.module.message;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.jenya705.mcapi.error.MessageTypeNotExistException;
import com.github.jenya705.mcapi.server.form.Form;
import com.github.jenya705.mcapi.server.form.FormMessage;
import com.github.jenya705.mcapi.server.form.FormPlatformProvider;
import com.github.jenya705.mcapi.server.module.mapper.JacksonProvider;
import com.github.jenya705.mcapi.server.module.mapper.Mapper;
import com.github.jenya705.mcapi.server.util.ExceptionableFunction;
import com.google.inject.Inject;
import lombok.SneakyThrows;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @author Jenya705
 */
public class MessageDeserializerImpl extends StdDeserializer<TypedMessage> implements MessageDeserializer {

    private final Map<String, ExceptionableFunction<JsonNode, Message>> messageDeserializers = new ConcurrentHashMap<>();

    private final JacksonProvider jacksonProvider;

    @Inject
    public MessageDeserializerImpl(Mapper mapper, JacksonProvider jacksonProvider, FormPlatformProvider formProvider) {
        super(TypedMessage.class);
        mapper
                .jsonDeserializer(TypedMessage.class, this)
                .jsonDeserializer(Message.class, this);
        this.jacksonProvider = jacksonProvider;
        addMessageType("default", node -> new DefaultMessage(node.asText()));
        addMessageType("form", node -> new FormMessage(
                jacksonProvider.getMapper().treeToValue(node, Form.class), formProvider
        ));
        addMessageType("component", obj -> new ComponentMessage(
                GsonComponentSerializer.gson().deserialize(obj.toString())
        ));
    }

    @Override
    public <T extends Message> void addMessageType(String type, Class<? extends T> messageClass, Consumer<T> processor) {
        addMessageType(type, node -> {
            T message = jacksonProvider.getMapper().treeToValue(
                    node, messageClass
            );
            processor.accept(message);
            return message;
        });
    }

    @Override
    public void addMessageType(String type, ExceptionableFunction<JsonNode, Message> deserializer) {
        messageDeserializers.put(type.toLowerCase(Locale.ROOT), deserializer);
    }

    @SneakyThrows
    @Override
    public TypedMessage deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        String type = node.get("type").asText().toLowerCase(Locale.ROOT);
        if (!messageDeserializers.containsKey(type)) {
            throw MessageTypeNotExistException.create(type);
        }
        return new TypedMessageImpl(
                type,
                messageDeserializers.get(type).accept(node.get("message"))
        );
    }
}

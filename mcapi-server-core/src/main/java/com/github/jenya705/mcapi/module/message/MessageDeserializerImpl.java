package com.github.jenya705.mcapi.module.message;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.jenya705.mcapi.Bean;
import com.github.jenya705.mcapi.OnStartup;
import com.github.jenya705.mcapi.error.MessageTypeNotExistException;
import com.github.jenya705.mcapi.form.Form;
import com.github.jenya705.mcapi.form.FormMessage;
import com.github.jenya705.mcapi.form.FormPlatformProvider;
import com.github.jenya705.mcapi.module.mapper.JacksonProvider;
import com.github.jenya705.mcapi.module.mapper.Mapper;
import com.github.jenya705.mcapi.util.ExceptionableFunction;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Jenya705
 */
public class MessageDeserializerImpl extends StdDeserializer<TypedMessage> implements MessageDeserializer {

    private final Map<String, ExceptionableFunction<JsonNode, SendMessage>> messageDeserializers = new HashMap<>();

    @Bean
    private JacksonProvider jacksonProvider;

    @Bean
    private Mapper mapper;

    @Bean
    private FormPlatformProvider formProvider;

    public MessageDeserializerImpl() {
        super(TypedMessage.class);
    }

    @OnStartup
    public void start() {
        mapper
                .jsonDeserializer(SendMessage.class, this);
        addMessageType("default", node -> new DefaultSendMessage(node.asText()));
        addMessageType("form", node -> new FormMessage(
                jacksonProvider.getMapper().treeToValue(node, Form.class), formProvider
        ));
    }

    @Override
    public <T extends SendMessage> void addMessageType(String type, Class<? extends T> messageClass, Consumer<T> processor) {
        addMessageType(type, node -> {
            T message = jacksonProvider.getMapper().treeToValue(
                    node, messageClass
            );
            processor.accept(message);
            return message;
        });
    }

    @Override
    public void addMessageType(String type, ExceptionableFunction<JsonNode, SendMessage> deserializer) {
        messageDeserializers.put(type.toLowerCase(Locale.ROOT), deserializer);
    }

    @SneakyThrows
    @Override
    public TypedMessage deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        String type = node.get("type").asText().toLowerCase(Locale.ROOT);
        if (!messageDeserializers.containsKey(type)) {
            throw new MessageTypeNotExistException(type);
        }
        return new TypedMessageImpl(
                type,
                messageDeserializers.get(type).accept(node.get("message"))
        );
    }
}

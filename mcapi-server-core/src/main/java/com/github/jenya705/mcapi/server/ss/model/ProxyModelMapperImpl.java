package com.github.jenya705.mcapi.server.ss.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jenya705.mcapi.server.module.mapper.JacksonProvider;
import com.github.jenya705.mcapi.server.module.mapper.Mapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jenya705
 */
@Singleton
public class ProxyModelMapperImpl implements ProxyModelMapper {

    private final Map<String, Class<?>> modelClasses = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;

    @Inject
    public ProxyModelMapperImpl(Mapper mapper) {
        if (mapper instanceof JacksonProvider) {
            this.objectMapper = ((JacksonProvider) mapper).getMapper();
            mapper.jsonDeserializer(ProxyModel.class, this::deserialize);
        }
        else {
            throw new IllegalArgumentException("Mapper is not JacksonProvider");
        }
    }

    private ProxyModel deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        JsonNode node = parser.readValueAsTree();
        String type = node.get("type").asText();
        Class<?> clazz = getModelClass(type);
        if (clazz == null) {
            throw new IOException(String.format("%s is not model type", type));
        }
        Object obj = objectMapper.convertValue(node.get("data"), clazz);
        return new ProxyModel(type, obj);
    }

    @Override
    public void addModel(String model, Class<?> modelClass) {
        modelClasses.put(model, modelClass);
    }

    @Override
    public Class<?> getModelClass(String model) {
        return modelClasses.get(model);
    }
}

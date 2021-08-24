package com.github.jenya705.mcapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;
import lombok.Getter;

/**
 * @author Jenya705
 */
@Provider
@JerseyClass
public class JacksonProvider implements ContextResolver<ObjectMapper> {

    @Getter
    private final static ObjectMapper mapper = new ObjectMapper();

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }
}

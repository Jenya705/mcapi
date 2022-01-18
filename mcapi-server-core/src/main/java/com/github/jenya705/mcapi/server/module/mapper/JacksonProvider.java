package com.github.jenya705.mcapi.server.module.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.ImplementedBy;

/**
 * @author Jenya705
 */
@ImplementedBy(MapperImpl.class)
public interface JacksonProvider {

    ObjectMapper getMapper();
}

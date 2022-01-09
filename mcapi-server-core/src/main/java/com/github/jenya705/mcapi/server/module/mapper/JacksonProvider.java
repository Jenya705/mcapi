package com.github.jenya705.mcapi.server.module.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Jenya705
 */
public interface JacksonProvider {

    ObjectMapper getMapper();
}

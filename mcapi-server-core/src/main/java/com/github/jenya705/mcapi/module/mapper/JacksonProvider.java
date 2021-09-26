package com.github.jenya705.mcapi.module.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Jenya705
 */
public interface JacksonProvider {

    ObjectMapper getMapper();

}

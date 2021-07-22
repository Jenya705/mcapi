package com.github.jenya705.mcapi.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.Getter;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;

/**
 * @author Jenya705
 */
public class ApiServerJacksonProvider extends JacksonJaxbJsonProvider {

    @Getter
    private static final ObjectMapper mapper = new ObjectMapper();

    public ApiServerJacksonProvider() {
        super();
        setMapper(mapper);
    }

}

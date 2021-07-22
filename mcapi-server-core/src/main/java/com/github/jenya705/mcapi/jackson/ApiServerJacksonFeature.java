package com.github.jenya705.mcapi.jackson;

import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.FeatureContext;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.ws.rs.ext.MessageBodyWriter;

/**
 * @author Jenya705
 */
public class ApiServerJacksonFeature implements Feature {

    @Override
    public boolean configure(FeatureContext context) {
        context.register(ApiServerJacksonProvider.class, MessageBodyReader.class, MessageBodyWriter.class);
        return true;
    }
}

package com.github.jenya705.mcapi;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.jenya705.mcapi.jackson.ApiServerJacksonProvider;
import com.github.jenya705.mcapi.jackson.JavaServerComponentParser;
import net.kyori.adventure.text.Component;

/**
 *
 * @since 1.0
 * @author Jenya705
 */
public class JavaServerApplication extends ApiServerApplication {

    public JavaServerApplication(JavaServerCore core) {
        super(core);
        SimpleModule module = new SimpleModule();
        module.addSerializer(Component.class, new JavaServerComponentParser.Serializer());
        module.addDeserializer(Component.class, new JavaServerComponentParser.Deserializer());
        ApiServerJacksonProvider.getMapper().registerModule(module);
        change(ApiServerPlayerRestController.class, JavaServerPlayerRestController.class);
    }

}

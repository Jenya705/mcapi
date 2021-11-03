package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.module.mapper.Mapper;
import net.kyori.adventure.text.Component;

/**
 * @author Jenya705
 */
public class JavaRestModule extends AbstractJavaApplicationModule {

    @OnStartup(priority = 1)
    public void start() {
        bean(Mapper.class)
                .jsonSerializer(Component.class, new ComponentJsonSerializer());
    }

}

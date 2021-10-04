package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.module.message.MessageDeserializer;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

/**
 * @author Jenya705
 */
public class JavaComponentMessageProvider extends AbstractJavaApplicationModule {

    @Bean
    private MessageDeserializer messageDeserializer;

    @OnStartup
    public void start() {
        messageDeserializer.addMessageType("component", obj ->
                new JavaComponentMessage(
                        GsonComponentSerializer.gson().deserialize(obj.toString())
                )
        );
    }
}

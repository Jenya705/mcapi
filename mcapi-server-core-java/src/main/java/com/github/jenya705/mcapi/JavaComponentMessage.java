package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.module.message.Message;
import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class JavaComponentMessage implements Message {

    private final Component component;

    @Override
    public void send(CommandSender sender) {
        if (sender instanceof JavaCommandSender) {
            ((JavaCommandSender) sender).sendMessage(component);
            return;
        }
        throw JavaPlatformUtils.notValidObject();
    }
}

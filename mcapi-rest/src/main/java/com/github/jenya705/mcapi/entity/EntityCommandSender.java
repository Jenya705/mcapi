package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.NamespacedKey;
import com.github.jenya705.mcapi.rest.RestCommandSender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityCommandSender implements CommandSender {

    private String id;
    private NamespacedKey type;

    @Override
    public void sendMessage(String message) {
        EntityUtils.throwEntityContextException();
    }

    @Override
    public void sendMessage(Component component) {
        EntityUtils.throwEntityContextException();
    }

    @Override
    public boolean hasPermission(String permission) {
        EntityUtils.throwEntityContextException();
        return true;
    }

    public RestCommandSender rest() {
        return RestCommandSender.from(this);
    }
}

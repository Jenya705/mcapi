package com.github.jenya705.mcapi.module.message;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.ApiOfflinePlayer;
import com.github.jenya705.mcapi.ApiPlayer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefaultMessage implements Message {

    private String message;

    @Override
    public void send(ApiCommandSender sender) {
        sender.sendMessage(message);
    }

    @Override
    public boolean ban(ApiOfflinePlayer player) {
        player.ban(message);
        return true;
    }

    @Override
    public boolean kick(ApiPlayer player) {
        player.kick(message);
        return true;
    }
}

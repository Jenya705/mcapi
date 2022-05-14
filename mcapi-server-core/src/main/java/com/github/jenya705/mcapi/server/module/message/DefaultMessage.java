package com.github.jenya705.mcapi.server.module.message;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.player.OfflinePlayer;
import com.github.jenya705.mcapi.player.Player;
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

    public static final String type = "default";

    private String message;

    @Override
    public void send(CommandSender sender) {
        sender.sendMessage(message);
    }

    @Override
    public boolean ban(OfflinePlayer player) {
        player.ban(message);
        return true;
    }

    @Override
    public boolean kick(Player player) {
        player.kick(message);
        return true;
    }

    @Override
    public TypedMessage type() {
        return new TypedMessageImpl(type, this);
    }
}

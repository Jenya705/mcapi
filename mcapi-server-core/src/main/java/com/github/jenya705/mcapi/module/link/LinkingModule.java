package com.github.jenya705.mcapi.module.link;

import com.github.jenya705.mcapi.LinkRequest;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.command.RootCommand;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.entity.BotEntity;

/**
 * @author Jenya705
 */
public interface LinkingModule {

    String linkCommand = "/" + RootCommand.name + " linkMenu";

    void requestLink(AbstractBot bot, Player player, LinkRequest request);

    void unlink(int id, Player player);

    default void unlink(BotEntity botEntity, Player player) {
        unlink(botEntity.getId(), player);
    }

    default void unlink(AbstractBot bot, Player player) {
        unlink(bot.getEntity(), player);
    }

    boolean isExists(Player player, int index, String permission);

    void toggle(Player player, int index, String permission);

    void update(Player player, int index);

    void end(Player player, int index, boolean enabled);
}

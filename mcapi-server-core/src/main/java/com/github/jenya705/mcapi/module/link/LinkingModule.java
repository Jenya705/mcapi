package com.github.jenya705.mcapi.module.link;

import com.github.jenya705.mcapi.ApiLinkRequest;
import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.command.RootCommand;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.entity.BotEntity;
import com.github.jenya705.mcapi.entity.RestLinkRequest;

/**
 * @author Jenya705
 */
public interface LinkingModule {

    String linkCommand = "/" + RootCommand.name + " linkMenu";

    void requestLink(AbstractBot bot, ApiPlayer player, ApiLinkRequest request);

    void unlink(int id, ApiPlayer player);

    default void unlink(BotEntity botEntity, ApiPlayer player) {
        unlink(botEntity.getId(), player);
    }

    default void unlink(AbstractBot bot, ApiPlayer player) {
        unlink(bot.getEntity(), player);
    }

    boolean isExists(ApiPlayer player, int index, String permission);

    void toggle(ApiPlayer player, int index, String permission);

    void update(ApiPlayer player, int index);

    void end(ApiPlayer player, int index, boolean enabled);
}

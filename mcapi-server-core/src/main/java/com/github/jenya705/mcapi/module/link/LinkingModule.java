package com.github.jenya705.mcapi.module.link;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.command.RootCommand;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.link.LinkRequest;

/**
 * @author Jenya705
 */
public interface LinkingModule {

    String linkCommand = "/" + RootCommand.name + " linkMenu";

    void requestLink(AbstractBot bot, ApiPlayer player, LinkRequest request);

    boolean isExists(ApiPlayer player, int index, String permission);

    void toggle(ApiPlayer player, int index, String permission);

    void update(ApiPlayer player, int index);

    void end(ApiPlayer player, int index, boolean enabled);
}

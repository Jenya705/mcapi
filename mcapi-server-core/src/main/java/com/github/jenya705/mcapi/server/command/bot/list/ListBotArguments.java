package com.github.jenya705.mcapi.server.command.bot.list;

import com.github.jenya705.mcapi.server.stringful.Argument;
import com.github.jenya705.mcapi.server.stringful.Index;
import lombok.Data;

/**
 * @author Jenya705
 */
@Data
public class ListBotArguments {

    private int page = 0;

    @Index(1)
    @Argument(required = false)
    private String player;

    @Index(0)
    @Argument(required = false)
    public void setPage(int page) {
        this.page = Math.max(page - 1, 0);
    }
}

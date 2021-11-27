package com.github.jenya705.mcapi.command.bot.permission.list;

import com.github.jenya705.mcapi.stringful.Argument;
import com.github.jenya705.mcapi.stringful.Index;
import lombok.Data;

/**
 * @author Jenya705
 */
@Data
public class PermissionListBotArguments {

    @Index(0)
    private String name;

    @Index(1)
    @Argument(required = false)
    private boolean token = false;

    private int page = 0;

    @Index(2)
    @Argument(required = false)
    public void setPage(int page) {
        this.page = Math.max(page - 1, 0);
    }

}

package com.github.jenya705.mcapi.command.bot.permission.give;

import com.github.jenya705.mcapi.OfflinePlayer;
import com.github.jenya705.mcapi.stringful.Argument;
import com.github.jenya705.mcapi.stringful.Index;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
public class PermissionGiveBotArguments {

    @Index(0)
    private String bot;

    @Index(1)
    private String permission;

    @Index(2)
    private boolean toggled;

    @Index(3)
    @Argument(required = false)
    private OfflinePlayer target = null; // identity

    @Index(4)
    @Argument(required = false)
    private boolean token = false;

    @Index(5)
    @Argument(required = false)
    private boolean regex = false;

}

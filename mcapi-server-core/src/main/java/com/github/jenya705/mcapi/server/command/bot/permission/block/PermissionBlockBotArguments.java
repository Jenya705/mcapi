package com.github.jenya705.mcapi.server.command.bot.permission.block;

import com.github.jenya705.mcapi.server.stringful.Argument;
import com.github.jenya705.mcapi.server.stringful.Index;
import com.github.jenya705.mcapi.world.World;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
public class PermissionBlockBotArguments {

    @Index(0)
    private String bot;

    @Index(1)
    private String permission;

    @Index(2)
    private boolean toggled;

    @Index(3)
    private int x;

    @Index(4)
    private int y;

    @Index(5)
    private int z;

    @Index(6)
    private String world = null; // current world of the player

    @Index(7)
    @Argument(required = false)
    private boolean token = false;

    @Index(8)
    @Argument(required = false)
    private boolean regex = false;

}

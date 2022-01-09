package com.github.jenya705.mcapi.server.command.bot.create;

import com.github.jenya705.mcapi.server.stringful.Argument;
import com.github.jenya705.mcapi.server.stringful.Index;
import lombok.Data;

/**
 * @author Jenya705
 */
@Data
public class CreateBotArguments {

    @Index(0)
    private String name;

    @Index(1)
    @Argument(required = false)
    private String player;
}

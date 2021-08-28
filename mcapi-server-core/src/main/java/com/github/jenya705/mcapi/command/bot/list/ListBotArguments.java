package com.github.jenya705.mcapi.command.bot.list;

import com.github.jenya705.mcapi.stringful.Argument;
import com.github.jenya705.mcapi.stringful.Index;
import lombok.Data;

/**
 * @author Jenya705
 */
@Data
public class ListBotArguments {

    @Index(0)
    @Argument(required = false)
    private String player;

}

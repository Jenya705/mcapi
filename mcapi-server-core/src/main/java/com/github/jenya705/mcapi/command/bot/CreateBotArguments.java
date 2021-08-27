package com.github.jenya705.mcapi.command.bot;

import com.github.jenya705.mcapi.stringful.Argument;
import com.github.jenya705.mcapi.stringful.Index;
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

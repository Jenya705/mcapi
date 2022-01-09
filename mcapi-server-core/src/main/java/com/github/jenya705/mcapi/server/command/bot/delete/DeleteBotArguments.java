package com.github.jenya705.mcapi.server.command.bot.delete;

import com.github.jenya705.mcapi.server.stringful.Argument;
import com.github.jenya705.mcapi.server.stringful.Index;
import lombok.Data;

/**
 * @author Jenya705
 */
@Data
public class DeleteBotArguments {

    @Index(0)
    private String token;

    @Index(1)
    @Argument(required = false)
    private String confirm = "";
}

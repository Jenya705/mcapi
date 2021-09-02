package com.github.jenya705.mcapi.command.link.unlink;

import com.github.jenya705.mcapi.stringful.Argument;
import com.github.jenya705.mcapi.stringful.Index;
import lombok.Data;

/**
 * @author Jenya705
 */
@Data
public class UnlinkArguments {

    @Index(0)
    private String botName;

    @Index(1)
    @Argument(required = false)
    private String player;

}

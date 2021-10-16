package com.github.jenya705.mcapi.mock;

import com.github.jenya705.mcapi.command.CommandExecutor;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jenya705
 */
@Getter
@AllArgsConstructor
public class MockCommand {

    private final String permission;
    private final CommandExecutor executor;

}

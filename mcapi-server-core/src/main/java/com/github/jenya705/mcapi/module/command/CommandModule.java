package com.github.jenya705.mcapi.module.command;

import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.OnStartup;
import com.github.jenya705.mcapi.command.RootCommand;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jenya705
 */
@Slf4j
public class CommandModule implements BaseCommon {

    @OnStartup
    public void start() {
        log.info("Registering root command...");
        core().addCommand("mcapi", new RootCommand().get(), "mcapi.command");
        log.info("Done! (Registering root command...)");
    }

}

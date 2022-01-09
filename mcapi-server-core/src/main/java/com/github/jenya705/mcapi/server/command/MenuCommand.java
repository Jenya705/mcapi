package com.github.jenya705.mcapi.server.command;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.server.ServerApplication;
import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.stringful.StringfulIterator;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author Jenya705
 */
@Slf4j
public abstract class MenuCommand implements CommandExecutor {

    private ServerApplication application;

    public MenuCommand() {}

    public MenuCommand(ServerApplication application) {
        this.application = application;
    }

    @Override
    public void onCommand(CommandSender sender, StringfulIterator args, String permission) {
        try {
            menuCommand(sender, args, permission);
        } catch (Exception e) {
            if (application.isDebug()) {
                log.error("Exception while executing menu command:", e);
            }
        }
    }

    public abstract void menuCommand(CommandSender sender, StringfulIterator args, String permission) throws Exception;

    @Override
    public List<CommandTab> onTab(CommandSender sender, StringfulIterator args, String permission) {
        return null;
    }

    @Override
    public void setConfig(ConfigData config) {
        /* NOTHING */
    }
}

package com.github.jenya705.mcapi.command;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.stringful.StringfulIterator;

import java.util.List;

/**
 * @author Jenya705
 */
public abstract class MenuCommand implements CommandExecutor {

    @Override
    public void onCommand(ApiCommandSender sender, StringfulIterator args, String permission) {
        try {
            menuCommand(sender, args, permission);
        } catch (Exception e) {
            // NOTHING
        }
    }

    public abstract void menuCommand(ApiCommandSender sender, StringfulIterator args, String permission) throws Exception;

    @Override
    public List<String> onTab(ApiCommandSender sender, StringfulIterator args, String permission) {
        return null;
    }

    @Override
    public void setConfig(ConfigData config) {
        /* NOTHING */
    }
}

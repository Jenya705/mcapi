package com.github.jenya705.mcapi.command;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.stringful.StringfulIterator;

import java.util.List;

/**
 * @author Jenya705
 */
public interface CommandExecutor {

    void onCommand(CommandSender sender, StringfulIterator args, String permission);

    List<CommandTab> onTab(CommandSender sender, StringfulIterator args, String permission);

    void setConfig(ConfigData config);

    default List<CommandTab> asyncTab(CommandSender sender, StringfulIterator args, String permission) {
        return onTab(sender, args, permission);
    }
}

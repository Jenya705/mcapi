package com.github.jenya705.mcapi.command;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.stringful.StringfulIterator;

import java.util.List;

/**
 * @author Jenya705
 */
public interface CommandExecutor {

    void onCommand(ApiCommandSender sender, StringfulIterator args, String permission);

    List<String> onTab(ApiCommandSender sender, StringfulIterator args, String permission);

    void setConfig(ConfigData config);

}

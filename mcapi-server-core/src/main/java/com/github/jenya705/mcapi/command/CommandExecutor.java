package com.github.jenya705.mcapi.command;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.stringful.StringfulIterator;

import java.util.List;

/**
 * @author Jenya705
 */
public interface CommandExecutor {

    void onCommand(ApiCommandSender sender, StringfulIterator args);

    List<String> onTab(ApiCommandSender sender, StringfulIterator args);

    void setConfig(ConfigData config);

}

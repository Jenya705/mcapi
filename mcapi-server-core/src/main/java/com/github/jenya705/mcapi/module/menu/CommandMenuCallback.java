package com.github.jenya705.mcapi.module.menu;

import com.github.jenya705.mcapi.menu.MenuCallback;
import com.github.jenya705.mcapi.menu.MenuCallbackType;
import com.github.jenya705.mcapi.player.Player;
import lombok.AllArgsConstructor;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class CommandMenuCallback implements MenuCallback {

    private final String command;

    @Override
    public void clicked(Player player) {
        player.runCommand(command);
    }

    @Override
    public MenuCallbackType getType() {
        return MenuCallbackType.RUN_COMMAND;
    }

    @Override
    public String getData() {
        return command;
    }
}

package com.github.jenya705.mcapi.menu;

import com.github.jenya705.mcapi.player.Player;

/**
 * @author Jenya705
 */
public interface MenuCallback {

    void clicked(Player player);

    MenuCallbackType getType();

    String getData();

}

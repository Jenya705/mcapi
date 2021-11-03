package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.inventory.JavaPlayerInventory;

/**
 * @author Jenya705
 */
public interface JavaPlayer extends Player, JavaCommandSender {

    @Override
    JavaPlayerInventory getInventory();

}

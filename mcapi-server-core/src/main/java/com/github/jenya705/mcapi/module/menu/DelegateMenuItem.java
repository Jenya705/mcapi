package com.github.jenya705.mcapi.module.menu;

import com.github.jenya705.mcapi.inventory.ItemStack;
import com.github.jenya705.mcapi.menu.MenuCallback;
import com.github.jenya705.mcapi.menu.MenuItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Delegate;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class DelegateMenuItem implements MenuItem {

    @Delegate
    private final ItemStack itemStackDelegate;

    @Getter
    private final MenuCallback callback;

}

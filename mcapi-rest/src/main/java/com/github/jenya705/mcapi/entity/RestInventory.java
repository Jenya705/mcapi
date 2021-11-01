package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.inventory.Inventory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestInventory {

    private int sizeX;
    private int sizeY;

    public static RestInventory from(Inventory inventory) {
        return new RestInventory(
                inventory.getSizeX(),
                inventory.getSizeY()
        );
    }

}

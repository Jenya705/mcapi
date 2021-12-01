package com.github.jenya705.mcapi.rest.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestInventoryItemStack {

    private int index;
    private RestItemStack item;

}

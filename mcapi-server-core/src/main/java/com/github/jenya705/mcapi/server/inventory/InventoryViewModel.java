package com.github.jenya705.mcapi.server.inventory;

import com.github.jenya705.mcapi.Material;
import com.github.jenya705.mcapi.inventory.InventoryView;
import com.github.jenya705.mcapi.inventory.ItemStack;
import com.github.jenya705.mcapi.menu.InventoryMenuView;
import com.github.jenya705.mcapi.server.ServerCore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryViewModel {

    private Material airMaterial;
    private ItemStack[] items;

    public Mono<InventoryView> createView(ServerCore core) {
        return core.createInventoryView(
                new InventoryContainer(getItems()),
                airMaterial
        );
    }

    public Mono<InventoryMenuView> createMenuView(ServerCore core) {
        return core.createInventoryMenuView(
                new InventoryContainer(items),
                airMaterial
        );
    }

}

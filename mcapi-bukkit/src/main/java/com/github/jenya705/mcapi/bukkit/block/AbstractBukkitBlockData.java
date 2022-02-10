package com.github.jenya705.mcapi.bukkit.block;

import com.github.jenya705.mcapi.bukkit.BukkitUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.block.Block;

import java.util.function.Consumer;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public abstract class AbstractBukkitBlockData<T extends org.bukkit.block.data.BlockData> {

    @Getter
    private final Block bukkitBlock;

    @SuppressWarnings("unchecked")
    protected T data() {
        return (T) bukkitBlock.getBlockData();
    }

    protected void updateData(Consumer<T> consumer) {
        T data = data();
        consumer.accept(data);
        BukkitUtils.notAsyncTask(() -> bukkitBlock.setBlockData(data));
    }

}

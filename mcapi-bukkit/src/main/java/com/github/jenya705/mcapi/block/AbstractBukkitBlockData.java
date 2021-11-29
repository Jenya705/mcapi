package com.github.jenya705.mcapi.block;

import com.github.jenya705.mcapi.BukkitUtils;
import lombok.AllArgsConstructor;
import org.bukkit.block.Block;

import java.util.function.Consumer;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public abstract class AbstractBukkitBlockData<T extends org.bukkit.block.data.BlockData> {

    private final Block block;

    @SuppressWarnings("unchecked")
    protected T data() {
        return (T) block.getBlockData();
    }

    protected void updateData(Consumer<T> consumer) {
        T data = data();
        consumer.accept(data);
        BukkitUtils.notAsyncTask(() -> block.setBlockData(data));
    }

}

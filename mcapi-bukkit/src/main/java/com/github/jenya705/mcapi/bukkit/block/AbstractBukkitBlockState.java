package com.github.jenya705.mcapi.bukkit.block;

import com.github.jenya705.mcapi.bukkit.block.state.CapturedState;
import com.github.jenya705.mcapi.bukkit.block.state.SharedCapturedState;
import lombok.Getter;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Jenya705
 */
@Getter
public abstract class AbstractBukkitBlockState<T extends org.bukkit.block.BlockState> implements BukkitStateContainer {

    private final org.bukkit.block.Block bukkitBlock;
    private final CapturedState state;

    public AbstractBukkitBlockState(org.bukkit.block.Block block) {
        this(new SharedCapturedState(block), block);
    }

    public AbstractBukkitBlockState(CapturedState state, org.bukkit.block.Block block) {
        bukkitBlock = block;
        this.state = state;
    }

    public boolean equals(Object other) {
        if (other == this) return true;
        if (other instanceof AbstractBukkitBlockState bukkitBlockState) {
            return Objects.equals(bukkitBlockState.bukkitBlock, bukkitBlock);
        }
        return false;
    }

    protected T state() {
        return state.state();
    }

    public void updateState(Consumer<T> consumer) {
        consumer.accept(state());
        state.updateState();
    }

    public void updateState() {
        state.updateState();
    }

}

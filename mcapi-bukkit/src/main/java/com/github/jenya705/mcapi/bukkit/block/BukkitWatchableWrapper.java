package com.github.jenya705.mcapi.bukkit.block;

import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.block.Watchable;
import com.github.jenya705.mcapi.bukkit.wrapper.BukkitWrapper;
import com.github.jenya705.mcapi.bukkit.block.state.CapturedState;
import com.github.jenya705.mcapi.player.Player;
import lombok.RequiredArgsConstructor;
import org.bukkit.block.Container;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@RequiredArgsConstructor
public class BukkitWatchableWrapper implements Watchable {

    private final CapturedState state;

    @Override
    public List<? extends Player> getWatchers() {
        return state
                .<Container>state()
                .getInventory()
                .getViewers()
                .stream()
                .filter(it -> it instanceof org.bukkit.entity.Player)
                .map(it -> BukkitWrapper.player((org.bukkit.entity.Player) it))
                .collect(Collectors.toList());
    }

    @Override
    public Block getBlock() {
        return BukkitWrapper.block(state.getBlock());
    }
}

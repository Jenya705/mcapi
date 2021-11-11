package com.github.jenya705.mcapi.block;

import com.github.jenya705.mcapi.BukkitWrapper;
import com.github.jenya705.mcapi.Player;
import com.github.jenya705.mcapi.block.data.Watchable;
import com.github.jenya705.mcapi.block.state.CapturedState;
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
}

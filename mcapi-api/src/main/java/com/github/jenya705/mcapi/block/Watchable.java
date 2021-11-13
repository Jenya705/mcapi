package com.github.jenya705.mcapi.block;

import com.github.jenya705.mcapi.Player;

import java.util.List;

/**
 * @author Jenya705
 */
public interface Watchable {

    List<? extends Player> getWatchers();

}

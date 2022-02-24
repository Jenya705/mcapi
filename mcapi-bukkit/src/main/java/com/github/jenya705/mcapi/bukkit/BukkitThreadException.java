package com.github.jenya705.mcapi.bukkit;

/**
 *
 * Indicates that this operation is illegal to execute in main bukkit thread
 *
 * @author Jenya705
 */
public class BukkitThreadException extends RuntimeException {

    public BukkitThreadException(String message) {
        super(message);
    }

}

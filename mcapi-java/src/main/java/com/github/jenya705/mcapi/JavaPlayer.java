package com.github.jenya705.mcapi;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Java minecraft player interface
 *
 * @author Jenya705
 */
public interface JavaPlayer {

    /**
     *
     * Returns name of player
     *
     * @return Name of player
     */
    @NotNull String getName();

    /**
     *
     * Returns uuid of player
     *
     * @return Uuid of player
     */
    @NotNull UUID getUniqueId();

    /**
     *
     * Returns exp of player
     *
     * @return Exp of player
     */
    float getExp();

    /**
     *
     * Returns level of player
     *
     * @return Level of player
     */
    int getLevel();

    /**
     *
     * Returns display name of player
     *
     * @return Display name of player
     */
    Component getDisplayName();

    /**
     *
     * Returns played time of player
     *
     * @return Played time of player
     */
    long getPlayerTime();

    /**
     *
     * Send message to player
     *
     * @param message Message
     */
    void sendMessage(Component message);


}

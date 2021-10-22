package com.github.jenya705.mcapi.selector;

import java.util.UUID;

/**
 * @author Jenya705
 */
public class PlayerSelector extends OfflinePlayerSelector {

    public static final PlayerSelector all = new PlayerSelector("@a", ".@a", null);
    public static final PlayerSelector random = new PlayerSelector("@r", ".@r", null);
    public static final PlayerSelector linked = new PlayerSelector("@l", ".@l", null);

    public static PlayerSelector of(UUID uuid) {
        return new PlayerSelector(uuid.toString(), "", uuid);
    }

    public static PlayerSelector of(String name) {
        return new PlayerSelector(name, "", null);
    }

    protected PlayerSelector(String stringValue, String permission, UUID target) {
        super(stringValue, permission, target);
    }
}
